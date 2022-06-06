<template>
  <div class="bg-light">
    <main class="form-signin">
      <form class="mx-auto">
        <router-link to="/">
          <h1 class="text-center mb-3">e-Shop</h1>
        </router-link>
        <h1 class="h3 fw-normal mt-1">Ве молиме регистрирајте се</h1>
        <label for="inputUsername" class="visually-hidden mt-1"
          >Корисничко име</label
        >
        <input
          v-model="username"
          type="text"
          id="inputUsername"
          class="form-control"
          required
          autofocus
        />
        <label for="inputName" class="visually-hidden mt-1">Име</label>
        <input
          v-model="name"
          type="text"
          id="inputName"
          class="form-control"
          required
          autofocus
        />
        <label for="inputSurame" class="visually-hidden mt-1">Презиме</label>
        <input
          v-model="surname"
          type="text"
          id="inputSurame"
          class="form-control"
          required
          autofocus
        />
        <label for="inputEmail" class="visually-hidden mt-1"
          >Email адреса</label
        >
        <input
          v-model="email"
          type="email"
          id="inputEmail"
          class="form-control"
          required
          autofocus
        />
        <label for="inputPassword" class="visually-hidden mt-1">Пасворд</label>
        <input
          v-model="password"
          type="password"
          id="inputPassword"
          class="form-control"
          required
        />
        <label for="inputRepeatPassword" class="visually-hidden mt-1"
          >Повторете го пасвордот
        </label>
        <input
          v-model="repeat"
          type="password"
          id="inputRepeatPassword"
          class="form-control"
          required
        />
        <div v-show="errorMessage != undefined" class="text-danger">
          {{ errorMessage }}
        </div>
        <button
          class="w-100 btn btn-lg btn-primary mt-1"
          type="submit"
          @click.prevent="register()"
        >
          Регистрирај се
        </button>
        <div class="text-center">
          Имате акаунт?
          <router-link to="/login"> Логирај се</router-link>
        </div>
      </form>
    </main>
  </div>
</template>
<script>
import MemberService from "@/repositories/memberRepository";
export default {
  data() {
    return {
      email: undefined,
      password: undefined,
      repeat: undefined,
      name: undefined,
      surname: undefined,
      username: undefined,
      errorMessage: undefined,
      user: {},
    };
  },
  methods: {
    register() {
      MemberService.registration(
        this.username,
        this.email,
        this.password,
        this.repeat,
        this.name,
        this.surname
      )
        .then((response) => {
          this.user = response.data;
          this.$store.commit("doLogIn", this.user);
          MemberService.verify(0, this.user.username)
            .then((response) => {
              this.$store.commit("doLogIn", response.data);
              this.successMessage = "Успешна верификација!";
              let vm = this;
              setTimeout(function () {
                vm.$router.push("/");
              }, 1000);
            })
            .catch(() => {
              this.errorMessage =
                "Неуспешна верификација, обидете се повторно!";
            });
        })
        .catch(() => {
          this.errorMessage = "Настана некаква грешка, обидете се повторно!";
        });
    },
  },
};
</script>

<style scoped>
.form-signin {
  height: 100vh;
  display: flex;
  align-items: center;
}
</style>