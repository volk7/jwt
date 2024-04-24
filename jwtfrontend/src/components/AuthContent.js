import * as React from 'react';
import { request } from '../axios_helper';

export default class AuthContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            error: null
        };
    }

    componentDidMount() {
        const { isAdmin } = this.props; // Assuming you pass isAdmin prop to determine user's role
        console.log(isAdmin)
        let apiPath = isAdmin ? "/api/v1/admin" : "/api/v1/user";

        request(
            "GET",
            apiPath,
            {}
        ).then((response) => {
            this.setState({ data: response.data });
        }).catch((error) => {
            this.setState({ error: error.message });
        });
    }

    render() {
        const { data, error } = this.state;

        if (error) {
            return <div>Error: {error}</div>;
        }

        return (
            <div className='row justify-content-md-center'>
                <div className='col-4'>
                    <div className='card' style={{ width: "18rem" }}>
                        <div className='card-body'>
                            <h5 className='card-title'>Backend Response</h5>
                            <p className='card-text'>Content:</p>
                            <p>{data.message}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}