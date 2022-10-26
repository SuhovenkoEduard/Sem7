import net from 'net'
import { IAction } from './interfaces'
import { messages, CLIENT_IP, SERVER_PORT } from './constants'
import { clientActions } from './client_actions'

const client = new net.Socket()

const runClient = (actions: IAction[]) => {
  const currentActions = [...actions]

  const sendMessage = () => {
    const action = currentActions.shift()
    console.log(`ACTION: ${JSON.stringify(action, null, 2)}`)
    client.write(JSON.stringify(action))
  }

  client.connect(SERVER_PORT, CLIENT_IP, function () {
    console.log('Connected')
    if (currentActions.length) {
      sendMessage()
    }
  })

  client.on('data', function (data: string) {
    console.log('Received: ')
    console.log(JSON.parse(data))
    if (currentActions.length) {
      sendMessage()
    } else {
      client.destroy()
    }
  })

  client.on('close', function () {
    console.log(messages.CONNECTION_CLOSED)
  })
}

runClient(clientActions)
