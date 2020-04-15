import React, { Component } from "react";
import "../user/login/Page.css";

import { Link } from "react-router-dom";
import { getCurrentUserAdminGroups } from "../util/RestApiCaller";
class Home extends Component {
  state = {
    currentUserAdminGroups: []
  };
  componentDidMount = () => {

    this.fetchCurrentUserAdminGroups();
  };
  fetchCurrentUserAdminGroups() {
    getCurrentUserAdminGroups()
      .then(response => {
        this.setState({
          currentUserAdminGroups: response
        });
      })
      .catch(error => {
        this.setState({
          currentUserAdminGroups: []
        });
      });
  }
  render() {
    if (this.props.authenticated) {
      return (
        <div className="page-container">
          <div className="page-content">
            <div className="app-title">
              {this.props.authenticated ? (
                <h2>Welcome {this.props.username}</h2>
              ) : (
                <div></div>
              )}
            </div>
            <h3 className="page-title">Admin Dashboard</h3>
            <table id="groups">
              <tbody>{this.renderGroupsData()}</tbody>
            </table>
            <ul>
              <li>
                <Link
                  to={{
                    pathname: "/ChangeGroupName",
                    currentUserAdminGroups: this.state.currentUserAdminGroups
                  }}
                >
                  Change Group Name
                </Link>
              </li>
              <li>
                <Link
                  to={{
                    pathname: "/MembersDetails",
                    currentUserAdminGroups: this.state.currentUserAdminGroups
                  }}
                >
                  Member Details
                </Link>
              </li>
              <li>
                <Link
                  to={{
                    pathname: "/RemoveMembers",
                    currentUserAdminGroups: this.state.currentUserAdminGroups
                  }}
                >
                  Remove Members
                </Link>
              </li>
            </ul>
          </div>
        </div>
      );
    }
    return (
      <div className="page-container">
        <div className="page-content">
          <p className="page-title">Please login/signup to continue</p>
        </div>
      </div>
    );
  }
  renderGroupsData() {
    return this.state.currentUserAdminGroups.map(group => {
      return (
        <tr key={group.groupName}>
          <td className="page-title">
            <Link
              to={{
                pathname: "/members",
                groupName: group.groupName
              }}
            >
              {group.groupName}
            </Link>
          </td>
          <td className="page-title">{group.membersCount}</td>
          <td className="page-title">{group.groupCreationTime}</td>
          <td className="page-title">{group.adminId}</td>
        </tr>
      );
    });
  }
}

export default Home;
