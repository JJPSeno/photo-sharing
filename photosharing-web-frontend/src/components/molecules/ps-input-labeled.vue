<template>
  <ps-label :for="inputLabeledId">
    <slot/>
    <ps-input     
      :inputId="inputLabeledId"
      :inputType="inputLabeledType" 
      :inputClass="inputLabeledClass"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      /> 
  </ps-label>
</template>
<script setup lang="ts">
import psInput from '../atoms/ps-input.vue';
import psLabel from '../atoms/ps-label.vue';

defineProps({
  inputLabeledId: String,
  inputLabeledType: String,
  inputLabeledClass: String,
  inputLabeledPlaceholder: String,
  modelValue: String
})
defineEmits(['update:modelValue'])

</script>
<style scoped></style>