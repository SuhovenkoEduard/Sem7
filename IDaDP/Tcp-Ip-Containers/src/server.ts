import net from 'net'
import { IAction, IActionTypes, IUser, IUserData } from './interfaces'
import { JSON_DATA_FILE_PATH, messages, SERVER_PORT, SERVER_IP } from './constants'
import { JSONRepository } from './repositories/json/JSONRepository'
import { SqliteRepository } from './repositories/sqlite/sqliteRepository'
import { UserModel } from './repositories/sqlite/user.model'

const createServerResponse = ({ json, sql }: { json: any, sql: any }) => JSON.stringify({
  json: JSON.stringify(json),
  sql: JSON.stringify(sql),
})

net
  .createServer(function (socket) {
    const current = new Date()
    console.log(`${current.getHours()}:${current.getMinutes()}:${current.getSeconds()}:${current.getMilliseconds()}`)
    console.log('CONNECTED: ' + socket.remoteAddress + ':' + socket.remotePort)

    socket.on('data', async (data: Buffer) => {
      console.log(`DATA ${socket.remoteAddress}: ${data}`)

      const action: IAction = JSON.parse(data.toString())

      const jsonRepository = new JSONRepository(JSON_DATA_FILE_PATH)
      const sqliteRepository = new SqliteRepository(UserModel)

      switch (action.type) {
        case IActionTypes.readAll: {
          socket.write(
            createServerResponse({
              json: await jsonRepository.readAll(),
              sql: await sqliteRepository.readAll(),
            }),
          )
          break
        }
        case IActionTypes.create: {
          const userData: IUserData = action.data as IUserData
          socket.write(
            createServerResponse({
              json: await jsonRepository.create(userData.name, userData.age),
              sql: await sqliteRepository.create(userData.name, userData.age),
            }),
          )
          break
        }
        case IActionTypes.findById: {
          if ('id' in action.data) {
            socket.write(
              createServerResponse({
                json: await jsonRepository.findById(action.data.id),
                sql: await sqliteRepository.findById(action.data.id),
              }),
            )
          }
          break
        }
        case IActionTypes.findByName: {
          if ('name' in action.data) {
            socket.write(
              createServerResponse({
                json: await jsonRepository.findByName(action.data.name),
                sql: await sqliteRepository.findByName(action.data.name),
              }),
            )
          }
          break
        }
        case IActionTypes.findByAge: {
          if ('age' in action.data) {
            socket.write(
              createServerResponse({
                json: await jsonRepository.findByAge(action.data.age),
                sql: await sqliteRepository.findByAge(action.data.age),
              }),
            )
          }

          break
        }
        case IActionTypes.update: {
          const user: IUser = action.data as IUser
          socket.write(
            createServerResponse({
              json: await jsonRepository.update(user),
              sql: await sqliteRepository.update(user),
            }),
          )
          break
        }
        case IActionTypes.deleteById: {
          if ('id' in action.data) {
            const { id } = action.data
            socket.write(
              createServerResponse({
                json: await jsonRepository.deleteById(id),
                sql: await sqliteRepository.deleteById(id),
              }),
            )
          }
          break
        }
        default: {
          socket.write(`client message "${data}"`)
          break
        }
      }
    })
    socket.on('close',  () => {
      console.log('CLOSED: ' + socket.remoteAddress + ' ' + socket.remotePort)
      console.log()
    })
  })
  .listen(SERVER_PORT, SERVER_IP)
console.log(messages.SERVER_IS_LISTENING(SERVER_IP, SERVER_PORT))
