<template>
  <button 
    :class="btnClass"
    :type="btnType" 
    :name="btnName"
    @click="$emit('click', $event)"
  >
  <slot/>
  </button>
</template>
<script setup lang="ts">
const props = defineProps({
    btnClass: String,
    btnName: {
      type: String
    },
    btnType: {
      type: String,
      required: true,
      default: 'button',
      validator(value: string) {
        return ['button','submit'].includes(value)
      }
    }

})

</script>
<style scoped></style>