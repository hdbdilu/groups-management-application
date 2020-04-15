import { API_BASE_URL, ACCESS_TOKEN } from "../constants";

const request = options => {
  const headers = new Headers({
    "Content-Type": "application/json"
  });

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append(
      "Authorization",
      "Bearer " + localStorage.getItem(ACCESS_TOKEN)
    );
  }

  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  return fetch(options.url, options).then(response =>
    response.json().then(json => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
  );
};

export function getCurrentUser() {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: API_BASE_URL + "/currentUser",
    method: "GET"
  });
}
export function createGroup(groupRequest) {
  checkToken();
  return request({
    url: API_BASE_URL + "/create",
    method: "POST",
    body: JSON.stringify(groupRequest)
  });
}
export function joinGroup(groupRequest) {
  checkToken();
  return request({
    url: API_BASE_URL + "/join",
    method: "PUT",
    body: JSON.stringify(groupRequest)
  });
}
const checkToken = () => {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }
};

export function login(loginRequest) {
  return request({
    url: API_BASE_URL + "/auth/login",
    method: "POST",
    body: JSON.stringify(loginRequest)
  });
}

export function signup(signupRequest) {
  return request({
    url: API_BASE_URL + "/auth/register",
    method: "POST",
    body: JSON.stringify(signupRequest)
  });
}

export function searchGroup(searchRequest) {
  checkToken();
  return request({
    url: API_BASE_URL + "/search",
    method: "POST",
    body: JSON.stringify(searchRequest)
  });
}

export function getCurrentUserAdminGroups() {
  checkToken();
  return request({
    url: API_BASE_URL + "/currentUserAdminGroups",
    method: "GET"
  });
}

export function changeGroupName(changeGroupNameRequest) {
  checkToken();
  return request({
    url: API_BASE_URL + "/changeGroupName",
    method: "PUT",
    body: JSON.stringify(changeGroupNameRequest)
  });
}

export function removeMember(updateGroupRequest) {
  checkToken();
  return request({
    url: API_BASE_URL + "/removeMember",
    method: "DELETE",
    body: JSON.stringify(updateGroupRequest)
  });
}
export function getMembers(membersRequest) {
  checkToken();
  return request({
    url: API_BASE_URL + "/members",
    method: "POST",
    body: JSON.stringify(membersRequest)
  });
}
export function getAllGroups() {
  checkToken();
  return request({
    url: API_BASE_URL + "/allGroups",
    method: "GET"
  });
}
export function getCurrentUserGroups() {
  checkToken();
  return request({
    url: API_BASE_URL + "/joinedGroups",
    method: "GET"
  });
}
