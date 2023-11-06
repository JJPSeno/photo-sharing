<template>
  <RouterView /> 
</template>
<script setup lang="ts">
import { RouterView } from 'vue-router'
import {useUser} from './compostables/useUser.ts'
import {onMounted} from 'vue'
onMounted(() => {
  fetch("http://localhost:9000/init")
    .then(response => {
      console.log("db initilaized")
      response.toString()
    })
    .then(data => {
        console.log(data)
    })
    .catch(error => console.error('Error fetching data:', error)); 
})
</script>

<style scoped>

</style>
