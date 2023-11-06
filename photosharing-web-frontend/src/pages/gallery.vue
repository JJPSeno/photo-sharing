<template>
  <main>
    <h1>The Gallery!</h1>
    <p>You made it!</p>
    <button @click="userStore.logout">Logout</button>
  </main>
</template>
<script setup lang="ts">
import router from '@/router';
import { useUser } from '@/compostables/useUser';
import {onMounted} from 'vue'
const userStore = useUser()
onMounted(() => {
if (userStore.loggedIn === false){
  router.push('/login')
}
})
</script>
<style scoped></style>