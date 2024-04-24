import * as React from 'react';


import { request, setAuthHeader } from '../axios_helper';

import Buttons from './Buttons';
import AuthContent from './AuthContent';
import LoginForm from './LoginForm';
import WelcomeContent from './WelcomeContent'

export default class AppContent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            componentToShow: "welcome",
            isAdmin: false // Initialize isAdmin state here
        }
    };

    login = () => {
        this.setState({componentToShow: "login"})
    };

    logout = () => {
        request(
            "GET",
            "/logout",
            {
            }).then(
            (response) => {
                this.setState({componentToShow: "welcome"})
                setAuthHeader(null);        
            }).catch(
            (error) => {
                setAuthHeader(null);
                this.setState({componentToShow: "welcome"})
            }
        );
    };

    onLogin = (e, username, password) => {
        e.preventDefault();
        request(
            "POST",
            "/api/v1/auth/login",
            {
                email: username,
                password: password
            }).then(
            (response) => {
                console.log(response.data);
                setAuthHeader(response.data.access_token);
                // Determine isAdmin value based on application logic
                const isAdmin = response.data.isAdmin; // Assuming isAdmin is returned from login API
                this.setState({componentToShow: "messages", isAdmin});
            }).catch(
            (error) => {
                setAuthHeader(null);
                this.setState({componentToShow: "welcome"})
            }
        );
    };

    onRegister = (event, firstName, lastName, username, password) => {
        event.preventDefault();
        request(
            "POST",
            "/api/v1/auth/signup",
            {
                firstName: firstName,
                lastName: lastName,
                email: username,
                password: password
            }).then(
            (response) => {
                setAuthHeader(response.data.access_token);
                // Determine isAdmin value based on application logic
                const isAdmin = response.data.isAdmin; // Assuming isAdmin is returned from login API
                this.setState({componentToShow: "messages", isAdmin});
            }).catch(
            (error) => {
                setAuthHeader(null);
                this.setState({componentToShow: "welcome"})
            }
        );
    };

  render() {
    return (
      <>
        <Buttons
          login={this.login}
          logout={this.logout}
        />

        {this.state.componentToShow === "welcome" && <WelcomeContent /> }
        {this.state.componentToShow === "login" && <LoginForm onLogin={this.onLogin} onRegister={this.onRegister} />}
        {this.state.componentToShow === "messages" && <AuthContent isAdmin={this.state.isAdmin} />}


      </>
    );
  };
}
