import React from 'react'
import { useState } from 'react'
import { StatusBar } from 'expo-status-bar'
import { Text, TouchableOpacity, View } from 'react-native'
import { styles } from './App.styles'

const App = () => {
  const [isChecked, setIsChecked] = useState<boolean>(false)
  return (
    <View style={styles.container}>
      <Text>Open up App.js to start working on your app!</Text>
      <Text>{isChecked ? "Checked" : "UnChecked"}</Text>
      <TouchableOpacity style={styles.button} onPress={() => setIsChecked(!isChecked)}>
        <Text>Change isChecked flag</Text>
      </TouchableOpacity>
      <StatusBar style="auto" />
    </View>
  )
}

export default App
