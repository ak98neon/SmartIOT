const jwtTokenName = "jwtToken";
const bearerPrefix = "Bearer ";
const authHeader = "Authorization";

function saveJwtTokenInLocalStorage(jwtToken) {
  localStorage.setItem(jwtTokenName, jwtToken);
}

function getJwtTokenFromLocalStorage() {
  return localStorage.getItem(jwtTokenName);
}

function sendAuthRequest(xhr, body) {
  xhr.setRequestHeader(
      authHeader, bearerPrefix + getJwtTokenFromLocalStorage());
  xhr.send(body);
}
