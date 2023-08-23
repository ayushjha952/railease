import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/Toast.css';

const TrainBySourceAndDestination = () => {
  const BASE_URL = "http://localhost:7426";
  const [source, setSource] = useState('');
  const [destination, setDestination] = useState('');
  const [trainOutputList, setTrainOutputList] = useState([]);
  const [isSearchClicked, setIsSearchClicked] = useState(false);
  const [showLoginPopup, setShowLoginPopup] = useState(false);
  const navigate = useNavigate();

  const handleSearchClick = async () => {
    try {
      const response = await axios.get(BASE_URL+`/searchtrainbysourceanddestination/${source}/${destination}`);
      const trainOutputModelList = response.data;
      setTrainOutputList(trainOutputModelList);
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

  const handleSourceChange = (event) => {
    setSource(event.target.value);
  };

  const handleDestinationChange = (event) => {
    setDestination(event.target.value);
  };

  const handleBookingClick = (selectedTrain) => {
    const customer = JSON.parse(localStorage.getItem('customer'));
    localStorage.setItem('selectedTrain', JSON.stringify(selectedTrain));
    if (!customer || !customer.custumerId) {
      setShowLoginPopup(true);
    } else {
      navigate('/booking');
    }
  };

  const handleLoginPopupClose = () => {
    setShowLoginPopup(false);
    navigate('/login');
  };

  return (
    <div
      style={{
        backgroundImage: `url('https://images.unsplash.com/photo-1647801955959-2ac9c15e16c5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center center',
        minHeight: '80vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: '3rem',
      }}
    >
      <div className="container mt-5">
        <div className="card mb-3" style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
          <div className="card-header" style={{ fontWeight: 'bold', fontFamily: 'Edu QLD Beginner', fontSize: '40px' }}>
            Search Train by Source and Destination
          </div>
          <div className="card-body">
            <ToastContainer toastClassName="custom-toast" style={{ width: '500px' }} />
            <div className="form-group">
              <label style={{ fontSize: '20px' }}>Source:</label>
              <input type="text" className="form-control" value={source} onChange={handleSourceChange} required />
            </div>
            <div className="form-group">
              <label style={{ fontSize: '20px' }}>Destination:</label>
              <input type="text" className="form-control" value={destination} onChange={handleDestinationChange} required />
            </div>
            <br />
            <button className="btn btn-primary" onClick={handleSearchClick} disabled={!source || !destination}>
              Search
            </button>
            <br />
            <br />
          </div>
        </div>

        {isSearchClicked && trainOutputList.length > 0 && (
          <div className="card mb-3" style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
            <div className="card-header" style={{ fontSize: '20px', fontWeight: 'bold' }}>Train Details</div>
            <div className="card-body">
              <div className="table-responsive">
                <table className="table table-striped" border="1">
                  <thead>
                    <tr>
                      <th>Train No</th>
                      <th>Train Name</th>
                      <th>Arrival Time</th>
                      <th>Departure Time</th>
                      <th>Duration</th>
                      <th>Source</th>
                      <th>Destination</th>
                      <th>First AC Fare:</th>
                      <th>Second AC Fare:</th>
                      <th>Sleeper Class Fare:</th>
                      <th>Booking</th>
                    </tr>
                  </thead>
                  <tbody>
                    {trainOutputList.map((train) => (
                      <tr key={train.trainNo}>
                        <td>{train.trainNo}</td>
                        <td>{train.trainName}</td>
                        <td>{train.arrivalTime}</td>
                        <td>{train.departureTime}</td>
                        <td>{train.duration}</td>
                        <td>{train.source}</td>
                        <td>{train.destination}</td>
                        <td>{train.ac1fare}</td>
                        <td>{train.ac2fare}</td>
                        <td>{train.slfare}</td>
                        <td>
                          <button className="btn btn-primary" onClick={() => handleBookingClick(train)}>
                            Book Now
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}

        {showLoginPopup && (
          <div className="modal d-block" tabIndex="-1" role="dialog">
            <div className="modal-dialog modal-dialog-centered" role="document">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title font-weight-bold text-uppercase" style={{ paddingLeft: '50px' }}>
                    Please log in first!!
                  </h5>
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
    </div>
  );
};

export default TrainBySourceAndDestination;


