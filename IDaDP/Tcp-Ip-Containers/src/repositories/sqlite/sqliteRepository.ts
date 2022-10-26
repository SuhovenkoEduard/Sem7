import { IRepository } from '../IRepository'
import { IUser } from '../../interfaces'

import Sequelize from 'sequelize'
import { UsersSchema } from '../../constants'

export class SqliteRepository implements IRepository {
  model:  Sequelize.ModelCtor<any>

  constructor(model:  Sequelize.ModelCtor<any>) {
    this.model = model
  }

  async readAll(): Promise<IUser[]> {
    return this.model.findAll({})
  }

  async create(name: string, age: number): Promise<string> {
    return this.model.create({
      name,
      age,
    })
  }

  async update(newUser: IUser): Promise<string> {
    return JSON.stringify(this.model
      .update(
        { ...newUser },
        { where: { [UsersSchema.id]: newUser.id } },
      ),
    )
  }

  async findById(id: number): Promise<IUser | null> {
    return this.model.findOne({
      where: { [UsersSchema.id]: id },
    })
  }

  async findByName(name: string): Promise<IUser[]> {
    return this.model.findAll({
      where: { [UsersSchema.name]: name },
    })
  }

  async findByAge(age: number): Promise<IUser[]> {
    return this.model.findAll({
      where: { [UsersSchema.age]: age },
    })
  }

  async deleteById(id: number): Promise<string> {
    return JSON.stringify(await this.model
      .destroy({ where: { [UsersSchema.id]: id } }))
  }
}
