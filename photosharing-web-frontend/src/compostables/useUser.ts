import { reactive, ref } from 'vue'
import { defineStore } from 'pinia'
import router from '@/router';

export const useUser = defineStore('user', () => {
  console.log("store loaded");
  const loggedIn = ref(false);
  const form = reactive({
    username: '',
    password: '',
  })

  const postForm = reactive({
    message: '',
    postAuthor: '',
    userId: 0
  })

  const clearForm = () => {
    form.username = ''
    form.password = ''
  }

  const signup = () => {
    console.log(`signup started. username: ${form.username} password: ${form.password}`)
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username: form.username, password: form.password })
    };
    fetch("http://localhost:9000/register", requestOptions)
      .then(response => {
        clearForm()
        response.toString()
      })
      .then(data => {
        console.log(`sign up success: ${data}`)
        router.push('/login')
      })
      .catch(error => console.error('Error fetching data:', error));
  }
  const login = () => {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username: form.username, password: form.password })
    };
    fetch("http://localhost:9000/login", requestOptions)
      .then(response => {
        clearForm()
        console.log(response)
        if (response.ok) {
          console.log("ok")
          loggedIn.value = true
          console.log(loggedIn.value)
          router.push('/')
        } else {
          console.log("not ok")
          alert("No User Exists or Wrong Password")
        }
      })
      .catch(error => console.error('Error fetching data:', error));
  }
  const logout = () => {
    loggedIn.value = false
    router.push('/login')
  }
  const post = () => {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      //val newPost = Post(message = formMessage, postAuthor = formPostAuthor, userId = formUserId)
      body: JSON.stringify({ message: postForm.message, postAuthor: postForm.password, userId: postForm.userId})
    };
    fetch("http://localhost:9000/posts", requestOptions)
      .then(response => {
        clearForm()
        response.toString()
      })
      .then(data => {
      })
      .catch(error => console.error('Error fetching data:', error));
  }
  return {
    loggedIn,
    form,
    postForm,
    signup,
    login,
    logout,
    post,
  }
})