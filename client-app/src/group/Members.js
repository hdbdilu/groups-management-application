import React, { Component } from "react";
import { getMembers } from "../util/RestApiCaller";
import Alert from "react-s-alert";
import "../user/login/Page.css";
class Members extends Component {
  state = {
    members: []
  };
  componentDidMount = () => {
    this.fetchMembers();
  };
  render() {
    return (
      <div className="page-container">
        <div className="page-content">
          <h2 className="page-title">Members</h2>
          <table id="groups">
            <tbody>{this.renderMembersData()}</tbody>
          </table>
        </div>
      </div>
    );
  }
  fetchMembers = () => {
    let memberRequest;
    if (this.props.location.groupName) {
      memberRequest = Object.assign(
        {},
        { groupName: this.props.location.groupName }
      );
    }
    if (this.props.location.state) {
      memberRequest = Object.assign(
        {},
        { groupName: this.props.location.state }
      );
    }

    getMembers(memberRequest)
      .then(response => {
        this.setState({ members: response });
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  };
  renderMembersData() {
    return this.state.members.map(member => {
      return (
        <tr key={member.email}>
          <td className="page-title">{member.email}</td>
          <td className="page-title">{member.name}</td>
        </tr>
      );
    });
  }
}

export default Members;
