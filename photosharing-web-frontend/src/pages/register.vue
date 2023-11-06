<template>
<ps-signup-template/>
<RouterLink to="/login">Login</RouterLink>
</template>
<script setup lang="ts">
import { RouterLink }  from 'vue-router'
import psSignupTemplate from '../components/templates/ps-signup-template.vue';
</script>
<style scoped></style>
