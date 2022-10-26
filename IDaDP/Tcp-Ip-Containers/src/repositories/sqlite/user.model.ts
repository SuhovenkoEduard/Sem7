import Sequelize from 'sequelize'
import { UsersSchema } from '../../constants'
import { TableNames } from '../../constants'
import { sequelize } from './dbConnection'

export const UserModel = sequelize.define<any, any>(TableNames.users, {
  [UsersSchema.id]: {
    type: Sequelize.INTEGER,
    primaryKey: true,
    autoIncrement: true,
    unique: true,
    allowNull: false,
  },
  [UsersSchema.name]: {
    type: Sequelize.TEXT,
    allowNull: false,
  },
  [UsersSchema.age]: {
    type: Sequelize.INTEGER,
    allowNull: false,
  },
}, {
  tableName: TableNames.users,
  timestamps: false,
})
