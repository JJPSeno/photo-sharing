<template>

<input
    :id="inputId"
    :class="inputClass"
    :type="inputType" 
    :placeholder="inputPlaceholder"
    :value="modelValue"
    @input="$emit('update:modelValue', $event.target.value)"
    > 

</template>
<script setup lang="ts">
defineProps({
  inputId: String,
  inputType: String,
  inputClass: String,
  inputPlaceholder: String,
  modelValue: String
})

defineEmits(['update:modelValue'])


</script>
<style scoped></style>