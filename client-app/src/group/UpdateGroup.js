import React, { Component } from "react";
import { changeGroupName, removeMember } from "../util/RestApiCaller";
import Alert from "react-s-alert";
import "../user/login/Page.css";
class UpdateGroup extends Component {
  state = {
    currentName: "",
    newName: "",
    memberEmail: ""
  };
  render() {
    if (this.props.location.currentUserAdminGroups) {
      if (this.props.isChangeGroupName) {
        return (
          <div className="page-container">
            <div className="page-content">
              <div>
                <h2 className="page-title">Update Group Name</h2>
                <form onSubmit={this.handleNameChange}>
                  <div className="form-item">
                    <select
                      value={this.state.currentName}
                      name="currentName"
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
                    <input
                      type="newName"
                      name="newName"
                      className="form-control"
                      placeholder="New Name"
                      value={this.state.newName}
                      onChange={this.handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-item">
                    <button type="submit" className="btn btn-block btn-primary">
                      Update
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        );
      }

      return (
        <div className="page-container">
          <div className="page-content">
            <div>
              <h2 className="page-title">Remove Member</h2>
              <form onSubmit={this.handleRemoveMember}>
                <div className="form-item">
                  <select
                    value={this.state.currentName}
                    name="currentName"
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
                  <input
                    type="memberEmail"
                    name="memberEmail"
                    className="form-control"
                    placeholder="Member Email"
                    value={this.state.memberEmail}
                    onChange={this.handleInputChange}
                    required
                  />
                </div>
                <div className="form-item">
                  <button type="submit" className="btn btn-block btn-primary">
                    Remove
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      );
    }
    return (
      <div className="page-container">
        <div className="page-content">
          <p className="page-title">Nothing to show</p>
        </div>
      </div>
    );
  }
  handleInputChange = event => {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value;
    this.setState({
      [inputName]: inputValue
    });
  };
  handleNameChange = event => {
    event.preventDefault();
    const changeGroupNameRequest = Object.assign({}, this.state);
    changeGroupName(changeGroupNameRequest)
      .then(response => {
        Alert.success(
          response.currentName + " name is changed to " + response.newName
        );
        this.props.history.push("/");
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  };
  handleRemoveMember = event => {
    event.preventDefault();
    const updateGroupRequest = Object.assign({}, this.state);
    removeMember(updateGroupRequest)
      .then(response => {
        Alert.success(
          response.memberEmail +
            "  is removed from group " +
            response.currentName
        );
        this.props.history.push("/");
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  };
}

export default UpdateGroup;
