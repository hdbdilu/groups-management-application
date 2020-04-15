import React, { Component } from "react";
import "../user/login/Page.css";
import "../user/login/Page.css";
class MemberDetails extends Component {
  state = {
    groupName: ""
  };
  render() {
    if (this.props.location.currentUserAdminGroups) {
      return (
        <div className="page-container">
          <div className="page-content">
            <h2 className="page-title">View Members</h2>
            <form onSubmit={this.fetchMembers}>
              <div className="form-item">
                <select
                  value={this.state.groupName}
                  name="groupName"
                  onChange={this.handleInputChange}
                  required
                >
                  <option key="" value="">
                    Select Group
                  </option>
                  {this.props.location.currentUserAdminGroups.map(group => (
                    <option key={group.groupName} value={group.groupName}>
                      {group.groupName}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-item">
                <button type="submit" className="btn btn-block btn-primary">
                  View Members
                </button>
              </div>
            </form>
          </div>
        </div>
      );
    }
    return (
      <div className="page-container">
        <div className="page-content">
          <h2 className="page-title">Go to Dashboard</h2>
        </div>
      </div>
    );
  }
  fetchMembers = () => {
    console.log(this.state.groupName);
    this.props.history.push("/members", this.state.groupName);
  };
  handleInputChange = event => {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value;

    this.setState({
      [inputName]: inputValue
    });
  };
}

export default MemberDetails;
