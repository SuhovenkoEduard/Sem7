import { getIpV4Address } from './ip'

export const messages = {
  SERVER_IS_LISTENING: (host: string, port: number) => `Server running on ${host}:${port}`,
  CONNECTION_CLOSED: 'Connection closed',
}

export const results = {
  added: 'added',
  notFound: 'not found',
  deleted: 'deleted',
  updated: 'updated',
}

export const LOCALHOST_IP = '127.0.0.1'
export const SERVER_IP = getIpV4Address() ?? LOCALHOST_IP
export const CLIENT_IP = process.argv[2] ?? SERVER_IP
export const SERVER_PORT = 1337
export const JSON_DATA_FILE_PATH = './res/data.json'
export const SQL_DATA_FILE_PATH = './res/data.db'

export const TableNames = {
  users: 'users',
}

export enum UsersSchema {
  id = 'id',
  name = 'name',
  age = 'age',
}
