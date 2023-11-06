<template>
  <p :class="paragraphClass">
    <slot />
  </p>
</template>
<script setup lang="ts">

defineProps({
  paragraphClass: String,
})

</script>
<style scoped></style>