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
              
               
                <h3>Welcome {this.props.username} to Admin Dashboard<p>You can Manage groups created by you here</p></h3>
               
                
              
            </div>
            <table className="page-title" align="center" id="groups" border-collapse="collapse" border="1">
              <tbody>
              <tr>
      <td className="page-title">Group Name</td>
      <td className="page-title">Members count</td>
      <td className="page-title">Creation Date</td>
      <td className="page-title">Admin Id</td>
    </tr>
    {this.renderGroupsData()}</tbody>
            </table>
            <div>
            <table className="app-title" align="center" margin="10px" padding="10px">
            <tbody>
              <tr >
                <td>
            <div>
              
                <Link
                  to={{
                    pathname: "/ChangeGroupName",
                    currentUserAdminGroups: this.state.currentUserAdminGroups
                  }}
                ><button className="btn  btn-primary">
                  Change Group Name
                  </button>
                </Link>
              </div>
              </td>
              </tr><tr>
              <td>
              <div>
                <Link
                  to={{
                    pathname: "/MembersDetails",
                    currentUserAdminGroups: this.state.currentUserAdminGroups
                  }}
                >
                 <button className="btn  btn-primary">
                  Member Details
                  </button>
                </Link>
              </div>
              </td></tr>
              <tr><td>
              <div>
                <Link
                  to={{
                    pathname: "/RemoveMembers",
                    currentUserAdminGroups: this.state.currentUserAdminGroups
                  }}
                >
                  <button className="btn  btn-primary">
                  Remove Members
                  </button>
                </Link>
              </div>
              </td></tr>
       
              </tbody>
              </table>
              </div>
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
          <td className="page-title">{this.formatDate(group.groupCreationTime.toString())}</td>
          <td className="page-title">{group.adminId}</td>
        </tr>
      );
    });
  }
  formatDate(datestring){
    var options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(datestring).toLocaleDateString([],options);
}
}

export default Home;
