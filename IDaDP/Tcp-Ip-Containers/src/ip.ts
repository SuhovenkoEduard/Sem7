import os from 'os'

export const getIpV4Address = (): string | null => {
  const addresses = Object.values(os.networkInterfaces()).flat()
    .filter(({ family, internal }) => family === 'IPv4' && !internal)
    .map(({ address }) => address)
  return addresses.length ? addresses[0] : null
}
