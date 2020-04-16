import React, { Component } from "react";
import { Route, Switch } from "react-router-dom";
import AppHeader from "../common/AppHeader";
import Login from "../user/login/Login";
import Signup from "../user/signup/Signup";
import Members from "../group/Members";
import MemberDetails from "../group/MemberDetails";
import Home from "../home/Home";
import Group from "../group/Group";
import GroupsJoined from "../group/GroupsJoined";
import UpdateGroup from "../group/UpdateGroup";
import OAuth2RedirectHandler from "../user/oauth2/OAuth2RedirectHandler";
import NotFound from "../common/NotFound";
import { getCurrentUser, getAllGroups } from "../util/RestApiCaller";
import { ACCESS_TOKEN } from "../constants";
import PrivateRoute from "../common/PrivateRoute";
import Alert from "react-s-alert";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import "./App.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      currentUser: null,
      currentUserAdminGroups: [],
      allGroups: []
    };
  }

  handleLogout = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    this.setState({
      authenticated: false,
      currentUser: null,
      userName: null
    });
    Alert.success("You're safely logged out!");
  };
  componentDidMount = () => {
    getCurrentUser()
      .then(response => {
        this.setState({
          currentUser: response,
          authenticated: true,
          userName: response.name
        });
      })
      .catch(error => {
        this.setState({});
      });

    getAllGroups()
      .then(response => {
        this.setState({
          allGroups: response
        });
      })
      .catch(error => {
        this.setState({
          allGroups: []
        });
      });

  };

  render() {
    return (
      <div className="app">
        <div className="app-top-box" background-color="#D6EAF8">
          <AppHeader
            authenticated={this.state.authenticated}
            onLogout={this.handleLogout}
          />
        </div>
        <div className="app-body">
          <Switch>
            <Route
              exact
              path="/"
              render={props => (
                <Home
                  authenticated={this.state.authenticated}
                  currentUser={this.state.currentUser}
                  username={this.state.userName}
                  {...props}
                />
              )}
            ></Route>
            <PrivateRoute
              exact
              path="/Home"
              render={props => (
                <Home
                  authenticated={this.state.authenticated}
                  currentUser={this.state.currentUser}
                  {...props}
                  username={this.state.userName}
                />
              )}
            ></PrivateRoute>
            <Route
              exact
              path="/Login"
              render={props => (
                <Login authenticated={this.state.authenticated} {...props} />
              )}
            ></Route>
            <Route
              path="/Signup"
              render={props => (
                <Signup authenticated={this.state.authenticated} {...props} />
              )}
            ></Route>
            <PrivateRoute
              path="/joinedGroups"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              component={GroupsJoined}
            ></PrivateRoute>
            <PrivateRoute
              path="/CreateGroup"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              component={Group}
            ></PrivateRoute>
            <PrivateRoute
              path="/JoinGroup"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              allGroups={this.state.allGroups}
              isJoinGroup="true"
              component={Group}
            ></PrivateRoute>
            <PrivateRoute
              path="/RemoveMembers"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              isRemoveMember="true"
              component={UpdateGroup}
            ></PrivateRoute>
            <PrivateRoute
              path="/SearchGroup"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              isSearchGroup="true"
              component={Group}
            ></PrivateRoute>
            <PrivateRoute
              path="/ChangeGroupName"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              isChangeGroupName="true"
              component={UpdateGroup}
            ></PrivateRoute>
            <PrivateRoute
              path="/members"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              component={Members}
            ></PrivateRoute>
            <PrivateRoute
              path="/MembersDetails"
              authenticated={this.state.authenticated}
              currentUser={this.state.currentUser}
              component={MemberDetails}
            ></PrivateRoute>
            <Route
              path="/oauth2/redirect"
              component={OAuth2RedirectHandler}
            ></Route>
            <Route component={NotFound}></Route>
          </Switch>
        </div>
        <Alert
          stack={{ limit: 3 }}
          timeout={3000}
          position="top-right"
          effect="slide"
          offset={65}
        />
      </div>
    );
  }
}

export default App;
