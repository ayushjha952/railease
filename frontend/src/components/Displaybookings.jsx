import React, { useState, useEffect } from 'react';
import { useNavigate} from 'react-router-dom';
import axios from 'axios';
import BookingCard from './BookingCard';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/Toast.css';

const BookingList = () => {
  const [bookings, setBookings] = useState([]);
  const [customerId, setCustomerId] = useState('');
  const [showLoginPopup, setShowLoginPopup] = useState(false);
  const navigate = useNavigate();

  console.log(bookings);

  useEffect(() => {
    const customer = JSON.parse(localStorage.getItem('customer'));
    if (customer && customer.custumerId) {
      setCustomerId(customer.custumerId);
    }
    else {
      setShowLoginPopup(true);
    }
  }, []);


  useEffect(() => {
    if (customerId) {
      fetchBookings();
    }
  }, [customerId]);

  const fetchBookings = async () => {
    try {
      const response = await axios.get(`http://localhost:7426/getallreservationsbycustomerid/${customerId}`);
      setBookings(response.data);
    } catch (error) {
      if (error.response && error.response.data) {
        const errorMessage = error.response.data.errorMessage;
        toast.error(`Error: ${errorMessage}`);
      } else {
        toast.error('Unknown error occurred during fetching reservations.');
      }
    }
  };

  const sortedBookings = bookings.sort((a, b) => {
    return b.reservationOutputModel.reservationId - a.reservationOutputModel.reservationId;
  });

  const handleLoginPopupClose = () => {
    setShowLoginPopup(false);
    navigate('/login');
  };

  return (
    <>
      <div style={{backgroundImage: `url('https://www.toptal.com/designers/subtlepatterns/uploads/circuit.png')`,}}>
        <div className="container mt-5">
          <br />
          <div className="container mt-5">

            <h1 style={{ fontFamily: 'Edu QLD Beginner', fontSize: '60px', textAlign: 'center' }}>Reservation History</h1>
            <br />
            <ToastContainer toastClassName="custom-toast" style={{ width: '500px' }} />
            <div className="row">
              <div className="card-group">
                {sortedBookings.length === 0 ? (
                  <h2 style={{ fontFamily: 'Edu QLD Beginner', height: '500px' }}>No Reservations Available</h2>
                ) : (
                  sortedBookings.map((booking) => (
                    <BookingCard key={booking.reservationOutputModel.reservationId} booking={booking} fetchBookings={fetchBookings} />
                  ))
                )}
              </div>
            </div>
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
        </div>
      </>
      );
};

      export default BookingList;