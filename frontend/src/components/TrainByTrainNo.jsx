import React, { useState } from 'react';
import { useNavigate} from 'react-router-dom';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/Toast.css';

const TrainByTrainNo = () => {
    const BASE_URL = "http://localhost:7426";
    const [trainNo, setTrainNo] = useState('');
    const [trainOutput, setTrainOutput] = useState([]);
    const [isSearchClicked, setIsSearchClicked] = useState(false);
    const [showLoginPopup, setShowLoginPopup] = useState(false);
    const navigate = useNavigate();



    const handleSearchClick = async () => {
        try {
            const response = await axios.get(BASE_URL+`/searchtrainbytrainno/${trainNo}`);
            const trainOutputModel = response.data;
            setTrainOutput(trainOutputModel);
            setIsSearchClicked(true);
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessage = error.response.data.errorMessage;
                toast.error(`Error: ${errorMessage}`);
            } else {
                toast.error('Unknown error occurred during fetching Train details.');
            }
        }
    };

    const handleTrainNoChange = (event) => {
        setTrainNo(event.target.value);
    };

    const handleBookingClick = () => {
        const customer = JSON.parse(localStorage.getItem('customer'));
        if (!customer || !customer.custumerId) {
            setShowLoginPopup(true);
        } else {
            localStorage.setItem('selectedTrain', JSON.stringify(trainOutput));
            navigate('/booking');
        }
    };

    const handleLoginPopupClose = () => {
        setShowLoginPopup(false);
        navigate('/login');
    };

    return (
        <>
            <body style={{
                backgroundImage: `url('https://images.unsplash.com/photo-1560521608-b4e1acca0824?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8aW5kaWFuJTIwcmFpbHdheXxlbnwwfHwwfHx8MA%3D%3D&w=1000&q=80')`,
                backgroundSize: 'cover',
                backgroundPosition: 'center center',
                minHeight: '80vh',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                marginTop: '3rem',
            }}>
            <div className="container mt-5" style={{ marginTop: '6rem !important' }}>
                    <div className="card mb-3" style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <div className="card-header" style={{ fontWeight: 'bold', fontFamily: 'Edu QLD Beginner', fontSize: '40px' }}>Search Train by Train No</div>
                        <div className="card-body">
                            <ToastContainer toastClassName="custom-toast" style={{ width: '500px' }} />
                            <div className="form-group">
                                <label style={{ fontSize: '20px' }}>Train No:</label>
                                <input type="text" className="form-control" value={trainNo} onChange={handleTrainNoChange} required />
                            </div>
                            <br />
                            <button className="btn btn-primary" onClick={handleSearchClick} disabled={!trainNo}>
                                Search
                            </button>
                            <br />
                            <br />
                        </div>
                    </div>

                    {isSearchClicked && trainOutput && (

                        <div className="card mb-3" style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                            <div className="card-header" style={{ fontSize: '20px', fontWeight: 'bold' }}>Train Details</div>
                            <div className="card-body">
                                <table className="table table-striped" border="1">
                                    <tbody>
                                        <tr>
                                            <td>Train No:</td>
                                            <td>{trainOutput.trainNo}</td>
                                        </tr>
                                        <tr>
                                            <td>Train Name:</td>
                                            <td>{trainOutput.trainName}</td>
                                        </tr>
                                        <tr>
                                            <td>Source:</td>
                                            <td>{trainOutput.source}</td>
                                        </tr>
                                        <tr>
                                            <td>Destination:</td>
                                            <td>{trainOutput.destination}</td>
                                        </tr>
                                        <tr>
                                            <td>Arrival Time:</td>
                                            <td>{trainOutput.arrivalTime}</td>
                                        </tr>
                                        <tr>
                                            <td>Departure Time:</td>
                                            <td>{trainOutput.departureTime}</td>
                                        </tr>
                                        <tr>
                                            <td>Duration:</td>
                                            <td>{trainOutput.duration}</td>
                                        </tr>
                                        <tr>
                                            <td>First AC Fare:</td>
                                            <td>{trainOutput.ac1fare}</td>
                                        </tr>
                                        <tr>
                                            <td>Second AC Fare:</td>
                                            <td>{trainOutput.ac2fare}</td>
                                        </tr>
                                        <tr>
                                            <td>Sleeper Class Fare:</td>
                                            <td>{trainOutput.slfare}</td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2" className="text-center">
                                                <button className="btn btn-primary btn-lg" onClick={handleBookingClick}>
                                                    Book Now
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    )}

                    {showLoginPopup && (
                        <div className="modal d-block" tabIndex="-1" role="dialog">
                            <div className="modal-dialog modal-dialog-centered" role="document">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title font-weight-bold text-uppercase" style={{ paddingLeft: '50px' }}>Please log in first!!</h5>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-primary" onClick={handleLoginPopupClose}>
                                            Close
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    )}
                </div>
            </body>
        </>
    );
};

export default TrainByTrainNo;
