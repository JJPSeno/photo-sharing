<template>
    <form name="signupForm" method="POST" @submit.prevent="userStore.signup">
      <ps-input-labeled 
        inputLabeledId = "input-signup-username" 
        inputLabeledType = "text"
        inputLabeledClass = "input-form"
        inputLabeledPlaceholder = "username"
        v-model = "userStore.form.username"
        >
        Username
      </ps-input-labeled>
      <ps-input-labeled 
        inputLabeledId = "input-signup-password" 
        inputLabeledType = "password"
        inputLabeledClass = "input-form" 
        inputLabeledPlaceholder = "password"
        v-model = "userStore.form.password"
        >
        Password
      </ps-input-labeled>
      <ps-button
      btnClass="button-form"
      btnType="submit"
      btnName="signup-submit">
        Submit
      </ps-button>
    </form>
</template>
<script setup lang="ts">
import psInputLabeled from '../molecules/ps-input-labeled.vue';
import psButton from '../atoms/ps-button.vue';
import { useUser } from '@/compostables/useUser';
const userStore = useUser();

</script>
<style scoped></style>