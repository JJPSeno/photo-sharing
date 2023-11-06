<template>
  <label :for="labelFor">
    <slot />
  </label>
</template>
<script setup lang="ts">

defineProps({
  labelFor: String,
})

</script>
<style scoped></style>