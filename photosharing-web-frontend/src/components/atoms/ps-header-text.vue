<template>
<component :is="tag">
  <slot/>
</component>
</template>
<script setup lang="ts">
defineProps({
  tag: {
    type: String,
    default: 'h1',
  }
})
</script>
<style scoped>
</style>