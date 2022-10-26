import { IRepository } from '../IRepository'
import { IUser } from '../../interfaces'
import fs from 'fs'
import { results } from '../../constants'

export class JSONRepository implements IRepository {
  filePath: string

  constructor(filePath: string) {
    this.filePath = filePath
  }

  private async writeAll(users: IUser[]) {
    fs.writeFileSync(this.filePath,
      JSON.stringify(users, null, '  '))
  }

  async readAll(): Promise<IUser[]> {
    const data = fs.readFileSync(this.filePath).toString()
    return JSON.parse(data)
  }

  async create(name: string, age: number): Promise<string> {
    const allUsers = await this.readAll()
    const maxId = Math.max(...allUsers.map(user => user.id), 0) + 1
    allUsers.push({
      id: maxId + 1,
      name: name,
      age: age,
    })

    await this.writeAll(allUsers)

    return results.added
  }

  async update(newUser: IUser): Promise<string> {
    const allUsers = await this.readAll()
    let exist = false

    const newAllUsers = allUsers.map((u) => {
      if (u.id === newUser.id) {
        exist = true
        return newUser
      } else {
        return u
      }
    })

    if (exist) {
      await this.writeAll(newAllUsers)
      return results.updated
    } else {
      return results.notFound
    }
  }

  async findById(id: number): Promise<IUser | null> {
    return (await this.readAll())
      .find((user) => user.id === id) || null
  }

  async findByName(name: string): Promise<IUser[]> {
    return (await this.readAll())
      .filter((user) => user.name === name)
  }

  async findByAge(age: number): Promise<IUser[]> {
    return (await this.readAll())
      .filter((user) => user.age === age)
  }

  async deleteById(id: number): Promise<string> {
    const allUsers = await this.readAll()

    let exist = false

    await this.writeAll(allUsers.filter(u => {
      if (u.id === id) exist = true
      return u.id !== id
    }))

    return exist ? results.deleted : results.notFound
  }
}
