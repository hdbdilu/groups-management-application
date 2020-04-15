import React, { Component } from "react";
import { Link, NavLink } from "react-router-dom";
import "./AppHeader.css";

class AppHeader extends Component {
  render() {
    return (
      <header className="app-header">
        <div align="center" className="container">
          <div>
            <Link className="app-title" to="/">
              Groups Management Portal
            </Link>
          </div>

          <div>
            <nav className="app-nav">
              {this.props.authenticated ? (
                <ul>
                  <li>
                    <NavLink to="/Home">Dashboard</NavLink>
                  </li>
                  <li>
                    <NavLink to="/joinedGroups">Joined Groups</NavLink>
                  </li>
                  <li>
                    <NavLink to="/SearchGroup">Search Group</NavLink>
                  </li>
                  <li>
                    <NavLink to="/CreateGroup">Create Group</NavLink>
                  </li>
                  <li>
                    <NavLink to="/JoinGroup">Join Group</NavLink>
                  </li>

                  <li>
                    <a onClick={this.props.onLogout}>Logout</a>
                  </li>
                </ul>
              ) : (
                <ul>
                  <li>
                    <NavLink to="/Home">Home</NavLink>
                  </li>
                  <li>
                    <NavLink to="/Login">Login</NavLink>
                  </li>
                  <li>
                    <NavLink to="/signup">Signup</NavLink>
                  </li>
                </ul>
              )}
            </nav>
          </div>
        </div>
      </header>
    );
  }
}

export default AppHeader;
