const jwtTokenName = "jwtToken";
const bearerPrefix = "Bearer ";
const authHeader = "Authorization";

function saveJwtTokenInLocalStorage(jwtToken) {
  document.cookie = jwtToken + "=" + jwtToken;
}

function getAuthCookie(cname) {
  let name = cname + "=";
  let ca = document.cookie.split(';');
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) === ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) === 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function sendRequestWithJwt(xhr, body) {
  xhr.setRequestHeader(
      authHeader, bearerPrefix + getAuthCookie(jwtTokenName));
  xhr.withCredentials = true;
  xhr.send(body);
}
