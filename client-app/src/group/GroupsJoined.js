import React, { Component } from "react";
import { getCurrentUserGroups } from "../util/RestApiCaller";
import Alert from "react-s-alert";
import "../user/login/Page.css";
class GroupsJoined extends Component {
  componentDidMount = () => {
    this.fetchGroups();
  };
  state = {
    currentUserGroups: []
  };
  render() {
    return (
      <div className="page-container">
        <div className="page-content">
          <h2 className="page-title">Joined Groups</h2>
          <table id="groups">
            <tbody>{this.renderGroupsData()}</tbody>
          </table>
        </div>
      </div>
    );
  }
  fetchGroups = () => {
    getCurrentUserGroups()
      .then(response => {
        this.setState({ currentUserGroups: response });
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  };
  renderGroupsData() {
    return this.state.currentUserGroups.map(group => {
      return (
        <tr key={group.groupName}>
          <td className="page-title">{group.groupName}</td>
          <td className="page-title">{group.adminName}</td>
        </tr>
      );
    });
  }
}

export default GroupsJoined;
