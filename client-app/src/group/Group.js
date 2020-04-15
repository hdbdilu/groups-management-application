import React, { Component } from "react";
import "../user/login/Page.css";
import Alert from "react-s-alert";
import { createGroup, joinGroup, searchGroup } from "../util/RestApiCaller";
class Group extends Component {
  state = {
    groupName: ""
  };
  render() {
    if (!this.props.isSearchGroup) {
      return (
        <div className="page-container">
          <div className="page-content">
            {this.props.isJoinGroup ? (
              <div>
                <h2 className="page-title">Join Group</h2>
                <form onSubmit={this.handleJoin}>
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
                      {this.props.allGroups.map(group => (
                        <option key={group.groupName} value={group.groupName}>
                          {group.groupName}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="form-item">
                    <button type="submit" className="btn btn-block btn-primary">
                      Join
                    </button>
                  </div>
                </form>
              </div>
            ) : (
              <div>
                <h2 className="page-title">Create Group</h2>
                <form onSubmit={this.handleCreate}>
                  <div className="form-item">
                    <input
                      type="groupName"
                      name="groupName"
                      className="form-control"
                      placeholder="Group Name"
                      value={this.state.groupName}
                      onChange={this.handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-item">
                    <button type="submit" className="btn btn-block btn-primary">
                      Create
                    </button>
                  </div>
                </form>
              </div>
            )}
          </div>
        </div>
      );
    }
    return (
      <div className="page-container">
        <div className="page-content">
          <h2 className="page-title">Search Group</h2>
          <form onSubmit={this.handleSearch}>
            <div className="form-item">
              <input
                type="groupName"
                name="groupName"
                className="form-control"
                placeholder="Group Name"
                value={this.state.groupName}
                onChange={this.handleInputChange}
                required
              />
            </div>
            <div className="form-item">
              <button type="submit" className="btn btn-block btn-primary">
                Search
              </button>
            </div>
          </form>
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
  handleCreate = event => {
    event.preventDefault();
    const groupRequest = Object.assign({}, this.state);
    createGroup(groupRequest)
      .then(response => {
        Alert.success(response.groupName + " created successfully!");
        this.props.history.push("/");
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  };
  handleJoin = event => {
    event.preventDefault();
    const joinRequest = Object.assign({}, this.state);
    joinGroup(joinRequest)
      .then(response => {
        Alert.success(response.groupName + " joined successfully!");
        this.props.history.push("/joinedGroups");
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  };
  handleSearch = event => {
    event.preventDefault();
    const searchRequest = Object.assign({}, this.state);
    searchGroup(searchRequest)
      .then(response => {
        if (!response.isJoined) {
          Alert.success(
            response.groupName +
              " is not joined by you. Click join group to join " +
              response.groupName +
              " is managed by " +
              response.adminName
          );
        } else {
          Alert.success(
            response.groupName +
              " is joined by you and is managed by " +
              response.adminName
          );
        }
      })
      .catch(error => {
        Alert.error(
          (error && error.message) ||
            "Oops! Something went wrong. Please try again!"
        );
      });
  };
}

export default Group;
