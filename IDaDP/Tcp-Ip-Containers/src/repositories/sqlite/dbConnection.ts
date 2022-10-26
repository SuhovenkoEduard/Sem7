import { Sequelize } from 'sequelize'
import { SQL_DATA_FILE_PATH } from '../../constants'

export const sequelize = new Sequelize('sqlite::memory:', {
  host: 'localhost', dialect: 'sqlite', pool: {
    max: 5, min: 0, idle: 10000,
  }, storage: SQL_DATA_FILE_PATH,
})
