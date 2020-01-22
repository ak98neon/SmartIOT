function authUser() {
  event.preventDefault();
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;
  let request = {};
  request.username = username;
  request.password = password;

  let xhr = new XMLHttpRequest();
  xhr.open("POST", "/auth/logIn");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.withCredentials = true;

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      let jwt = xhr.responseText;
      window.location.href = "http://" + window.location.host + "/iot";
    }
  };

  xhr.send(JSON.stringify(request));
}
