<template>
  <ps-login-template />
  <RouterLink to="/register">Register</RouterLink>
</template>
<script setup lang="ts">
import { RouterLink }  from 'vue-router'
import psLoginTemplate from '../components/templates/ps-login-template.vue';

</script>
<style scoped></style>