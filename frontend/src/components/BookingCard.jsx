import React, { useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/Toast.css';
import { OverlayTrigger, Popover } from 'react-bootstrap';


const BookingCard = ({ booking, fetchBookings }) => {
  const BASE_URL = "http://localhost:7426";
  const [showPopup, setShowPopup] = useState(false);
  const [showConfirmationModal, setShowConfirmationModal] = useState(false);
  const [cancellationDetails, setCancellationDetails] = useState(null);


  const popover = (
    <Popover id="popover-disabled">
      <Popover.Body style={{fontWeight:'bold',color:'red',fontFamily:'Playfair Display',fontSize:'17px'}}>Cannot Cancel the ticket on or after Travel Date.</Popover.Body>
    </Popover>
  );

  const cancelReservation = async () => {
    setShowConfirmationModal(true);
  };

  const confirmCancellation = async () => {
    try {
      const response = await axios.put(BASE_URL+`/cancelreservation/${booking.reservationOutputModel.reservationId}`);
      const cancellationOutput = response.data;
      toast.success('Ticket Cancelled Successfully!');
      setCancellationDetails(cancellationOutput);
      setShowPopup(true);
    } catch (error) {
      if (error.response && error.response.data) {
        const errorMessage = error.response.data.errorMessage;
        toast.error(`Error: ${errorMessage}`);
      } else {
        toast.error('Unknown error occurred during cancellation.');
      }
    }

    setShowConfirmationModal(false);
  };

  const cancelConfirmation = () => {
    setShowConfirmationModal(false);
  };

  const closePopup = () => {
    setShowPopup(false);
    setCancellationDetails(null);
    fetchBookings();
  };

  return (
    <div className="col mb-4">
      <div className="card border-dark mb-3 h-100 shadow" style={{ width: '25rem' }}>
        {/* <ToastContainer toastClassName="custom-toast" style={{ width: '500px' }} /> */}
        <img src="https://i.ibb.co/3drKVHD/reservation.png" className="card-img-top" alt="Booking" />
        <div className="card-body">
          <div className="table-responsive">
            <table className="table table-striped">
              <tbody>
                <tr>
                  <td>
                    <strong>Reservation ID:</strong> {booking.reservationOutputModel.reservationId}
                  </td>
                  <td>
                    {(booking.reservationOutputModel.bookingStatus === 'Cancelled') ? (
                      <span className="badge bg-danger text-white " style={{ fontFamily: 'cursive', fontSize: '20px', marginLeft: '40px' }}>Cancelled</span>
                    ) : (
                      <span className="badge bg-success text-white" style={{ fontFamily: 'cursive', fontSize: '20px', marginLeft: '60px' }}>Booked</span>
                    )}
                  </td>
                </tr>
                <tr>
                  <td>
                    <strong>Source:</strong> {booking.reservationOutputModel.source}
                  </td>
                  <td>
                    <strong>Destination:</strong> {booking.reservationOutputModel.destination}
                  </td>
                </tr>
                <tr>
                  <td>
                    <strong>No. of Seats:</strong> {booking.reservationOutputModel.noOfSeats}
                  </td>
                  <td>
                    <strong>Travel Date:</strong> <br /> {booking.reservationOutputModel.travelDate}
                  </td>
                </tr>
                <tr>
                  <td>
                    <strong>Train No:</strong> {booking.reservationOutputModel.trainNo}
                  </td>
                  <td>
                    <strong>Reservation Date:</strong> <br />{booking.reservationOutputModel.reservationDate}
                  </td>
                </tr>
                <tr>
                  <td>
                    <strong>Seat Type:</strong> {booking.reservationOutputModel.seatType}
                  </td>
                  <td>
                    <strong>Total Ticket Price:</strong> {booking.reservationOutputModel.totalTicketPrice}
                  </td>
                </tr>
                {booking.cancellationId && (
                  <>
                    <tr>
                      <td>
                        <strong>Cancellation ID:</strong> {booking.cancellationId}
                      </td>
                      <td>
                        <strong>Refund Amount:</strong> {booking.reservationOutputModel.totalTicketPrice-booking.refundAmount}
                      </td>
                    </tr>
                  </>
                )}
              </tbody>
            </table>
          </div>

          {booking.reservationOutputModel.bookingStatus === 'Booked' && booking.cancelledPossible === true && (
            <button className="btn btn-danger" onClick={cancelReservation} style={{ marginLeft: '100px' }}>
              Cancel Reservation
            </button>
          )}
          {booking.reservationOutputModel.bookingStatus === 'Booked' && booking.cancelledPossible === false && (
            <OverlayTrigger
              trigger={['hover', 'focus']}
              placement="right"
              overlay={popover}
            >
              <span className="d-inline-block" tabIndex="0">
                <button className="btn btn-danger" style={{ marginLeft: '100px' }} disabled>
                  Cancel Reservation
                </button>
              </span>
            </OverlayTrigger>
          )}

        </div>
      </div>

      {showPopup && (
        <div
          className="popup"
          style={{
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            position: 'fixed',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: '100',
          }}
        >
          <div
            className="popup-content"
            style={{
              backgroundColor: '#fff',
              padding: '40px',
              borderRadius: '5px',
              textAlign: 'center',
              border: '4px solid black',
            }}
          >
            <h2>Cancellation Details</h2>
            <hr />
            <button
              className="close-btn"
              style={{
                position: 'absolute',
                top: '10px',
                right: '10px',
                fontSize: '20px',
                cursor: 'pointer',
                border: 'none',
                backgroundColor: 'transparent',
                color: '#888',
              }}
              onClick={closePopup}
            >
              &times;
            </button>
            <p style={{ fontSize: 20, fontFamily: 'Playfair Display', fontWeight: 'bold' }}>
              Refund Amount for Your Cancelled Ticket is: {booking.reservationOutputModel.totalTicketPrice-cancellationDetails.refundAmount}
            </p>
            <hr />
            <button className="btn btn-info close-popup-btn" onClick={closePopup}>
              Close
            </button>
          </div>
        </div>
      )}

      <div
        className={`modal fade ${showConfirmationModal ? 'show' : ''}`}
        style={{ display: showConfirmationModal ? 'block' : 'none', marginTop: '3rem' }}
        tabIndex="-1"
        role="dialog"
        aria-labelledby="confirmModal"
        aria-hidden={!showConfirmationModal}
      >
        <div className="modal-dialog" role="document">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="confirmModal">
                Confirm Cancellation
              </h5>
              <button type="button" className="close" data-dismiss="modal" aria-label="Close" onClick={cancelConfirmation}>
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div className="modal-body">
              <p>Are you sure you want to cancel this reservation?</p>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-secondary" onClick={cancelConfirmation}>
                Cancel
              </button>
              <button type="button" className="btn btn-danger" onClick={confirmCancellation}>
                Confirm
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookingCard;