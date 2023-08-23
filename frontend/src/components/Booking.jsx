import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../css/Booking.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../css/Toast.css";

const MakeReservationComponent = () => {
  const BASE_URL = "http://localhost:7426";
  const [reservationInput, setReservationInput] = useState({
    noOfSeats: "",
    travelDate: "",
    seatType: "",
    source: "",
    destination: "",
    trainNo: "",
    customerId: "",
    passengerList: [],
  });
  const [reservationOutput, setReservationOutput] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [showLoginPopup, setShowLoginPopup] = useState(false);
  const [totalNoOfReservations, setTotalNoOfReservations] = useState(0);
  const [seatTypename, setSeatTypename] = useState("");
  const [counter, setCounter] = useState(0);
  const [price , setPrice] = useState(0);
  const navigate = useNavigate();
  

  const selectedTrain = JSON.parse(localStorage.getItem("selectedTrain"));
  const customer = JSON.parse(localStorage.getItem("customer"));

  useEffect(() => {
    if (selectedTrain) {
      setReservationInput((prevReservationInput) => ({
        ...prevReservationInput,
        trainNo: selectedTrain.trainNo,
        source: selectedTrain.source,
        destination: selectedTrain.destination,
      }));
    }
    if (customer) {
      setReservationInput((prevReservationInput) => ({
        ...prevReservationInput,
        customerId: customer.custumerId,
      }));
    }

    if(customer==null){
      setShowLoginPopup(true);
    }
  }, []);

  const seatTypes = ["AC1", "AC2", "SL"];
  const handleInputChange = async (event) => {
    setReservationInput({
      ...reservationInput,
      [event.target.name]: event.target.value,
    });
    console.log(reservationInput);
    if (event.target.name === "seatType" && selectedTrain != null  && reservationInput.travelDate != "") {
      const seatType = event.target.value;
      if (seatType === "AC1") {
        const price = selectedTrain.ac1fare;
        setPrice(price);
        console.log(selectedTrain.ac1fare);
        setSeatTypename("AC First Class (1A)");
      } else if (seatType === "AC2") {
        const price = selectedTrain.ac2fare;
        setPrice(price);
        setSeatTypename("AC 2 Tier (2A)");
      } else if (seatType === "SL") {
        const price = selectedTrain.slfare;
        setPrice(price);
        setSeatTypename("Sleeper (SL)");
      }
      const response = await axios.get(BASE_URL+`/totalnoofreservations/${reservationInput.trainNo}/${reservationInput.travelDate}/${seatType}`);
      const seats = selectedTrain.ac2seats - response.data;
      setTotalNoOfReservations(seats);
    }
    console.log(reservationInput);
  };

  const handleReservation = async (event) => {
    event.preventDefault();
    if (!customer || !customer.custumerId) {
      setShowLoginPopup(true);
      return;
    }
    try {
      const response = await axios.post(
        BASE_URL+"/addreservation",
        reservationInput
      );

      const reservationOutput = response.data;
      toast.success("Reservation successful");
      setReservationOutput(reservationOutput);
      console.log(reservationOutput);
      setIsModalOpen(true);
      setReservationInput({
        noOfSeats: "",
        travelDate: "",
        seatType: "",
        source: "",
        destination: "",
        trainNo: "",
        customerId: "",
        passengerList: [],
      });

      localStorage.removeItem("selectedTrain");
    } catch (error) {
      if (error.response && error.response.data) {
        const message = error.response.data.errorMessage;
        toast.error(`Error: ${message}`);
      } else {
        toast.error("Unknown error occurred during Ticket Reservation.");
      }
    }
  };

  const handlePassengerChange = (index, field, value) => {
    const passengerList = [...reservationInput.passengerList];
    passengerList[index][field] = value;
    setReservationInput({
      ...reservationInput,
      passengerList,
    });
    // console.log(passengerList[0]['name']);
  };
console.log(counter);
  const addPassenger = () => {
    if (counter < reservationInput.noOfSeats) {
      setReservationInput({
        ...reservationInput,
        passengerList: [
          ...reservationInput.passengerList,
          { passengerName: "", age: "", gender: "" }, 
        ],
      });
      setCounter(counter + 1);
    }
  };

  const removePassenger = (index) => {
    const passengerList = [...reservationInput.passengerList];
    passengerList.splice(index, 1); 
    setCounter(counter - 1);
    setReservationInput({
      ...reservationInput,
      passengerList,
    });
  };

  const closeModal = () => {
    setIsModalOpen(false);
    // console.log(reservationInput);
    navigate("/");
  };

  const handleLoginPopupClose = () => {
    setShowLoginPopup(false);
    navigate("/login");
  };

  return (
    <>
      <div style={{
        backgroundImage: `url('https://images.unsplash.com/photo-1559164317-11e4b62b5a26?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dHJhaW4lMjB0cmFja3xlbnwwfHwwfHx8MA%3D%3D&w=1000&q=80')`,
        // backgroundSize: 'cover',
        backgroundPosition: 'center',
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: '7px',
      }}>
        <div className="container mt-5">
          <br />
          <h2
            style={{
              fontFamily: "Edu QLD Beginner",
              fontSize: "50px",
              textAlign: "center",
              color: "white",
            }}
          >
            Make Reservation
          </h2>
          <br />
          <ToastContainer
            toastClassName="custom-toast"
            style={{ width: "500px" }}
          />
          <form onSubmit={handleReservation}>
            <div className="card mb-3" style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
              <div className="card-header" style={{ fontSize: '20px', fontWeight: 'bold' }}>Reservation Details</div>
              <div className="card-body">
                <table className="table table-bordered ">
                  <tbody>
                    <tr>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="noOfSeats">No. of Seats:</label>
                      </td>
                      <td>
                        <input
                          type="number"
                          id="noOfSeats"
                          name="noOfSeats"
                          className="form-control"
                          value={reservationInput.noOfSeats}
                          onChange={handleInputChange}
                          required
                          min="0"
                        />
                      </td>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="travelDate">Travel Date:</label>
                      </td>
                      <td>
                        <input
                          type="date"
                          id="travelDate"
                          name="travelDate"
                          className="form-control"
                          value={reservationInput.travelDate}
                          onChange={handleInputChange}
                          required
                          min={new Date().toISOString().split("T")[0]}
                          max={
                            new Date(new Date().setDate(new Date().getDate() + 90))
                              .toISOString()
                              .split("T")[0]
                          }
                        />
                      </td>
                    </tr>
                    <tr>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="seatType">Seat Type:</label>
                      </td>
                      <div style={{ position: "relative" }}>
                        <select
                          id="seatType"
                          name="seatType"
                          className="form-control"
                          value={reservationInput.seatType}
                          onChange={handleInputChange}
                          required
                          style={{ width: "100%", boxSizing: "border-box" }}
                        >
                          <option value="">Select Seat Type</option>
                          {seatTypes.map((type) => (
                            <option key={type} value={type}>
                              {type}
                            </option>
                          ))}
                        </select>
                      </div>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="source">Source:</label>
                      </td>
                      <td>
                        <input
                          type="text"
                          id="source"
                          name="source"
                          className="form-control"
                          disabled
                          required
                          value={reservationInput.source}
                          onChange={handleInputChange}
                        />
                      </td>
                    </tr>
                    <tr>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="destination">Destination:</label>
                      </td>
                      <td>
                        <input
                          type="text"
                          id="destination"
                          name="destination"
                          className="form-control"
                          disabled
                          required
                          value={reservationInput.destination}
                          onChange={handleInputChange}
                        />
                      </td>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="trainNo">Train No:</label>
                      </td>
                      <td>
                        <input
                          type="text"
                          id="trainNo"
                          name="trainNo"
                          className="form-control"
                          disabled
                          required
                          value={reservationInput.trainNo}
                          onChange={handleInputChange}
                        />
                      </td>
                    </tr>
                    <tr>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="destination">Train Name:</label>
                      </td>
                      <td>
                        <input
                          type="text"
                          id="destination"
                          name="destination"
                          className="form-control"
                          disabled
                          required
                          value={(selectedTrain != null) ? selectedTrain.trainName : ""}
                          onChange={handleInputChange}
                        />
                      </td>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="seatCount">{seatTypename} Seats:</label>
                      </td>
                      <td>
                        <input
                          type="text"
                          id="seatCount"
                          name="seatCount"
                          className="form-control"
                          disabled
                          required
                          value={totalNoOfReservations}
                          onChange={handleInputChange}
                        />
                      </td>
                    </tr>
                    <tr>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
                        <label htmlFor="customerId">Customer ID:</label>
                      </td>
                      <td>
                        <input
                          type="text"
                          id="customerId"
                          name="customerId"
                          className="form-control"
                          disabled
                          required
                          value={reservationInput.customerId}
                          onChange={handleInputChange}
                        />
                      </td>
                      <td style={{ background: 'rgba(255, 255, 255, 0.8)' }}>Total Ticket Price:</td>
                      <td>
                      <input
                          type="number"
                          id="ticketPrice"
                          name="ticketPrice"
                          className="form-control"
                          disabled
                          required
                          value={price*reservationInput.noOfSeats}
                          onChange={handleInputChange}
                        />
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div> 

            <div className="card mb-3" style={{ background: 'rgba(255, 255, 255, 0.8)' }}>
              <div className="card-header" style={{ fontSize: '20px', fontWeight: 'bold' }}>Passengers Details</div>
              <div className="card-body">
                <table className="table table-bordered">
                  <tbody>
                    {reservationInput.passengerList.map((passenger, index) => (
                      <tr key={index}>
                        <td>
                          <label htmlFor={`passengerName${index}`}>Name:</label>
                          <input
                            type="text"
                            id={`passengerName${index}`}
                            name={`passengerName${index}`}
                            className="form-control"
                            placeholder="Name"
                            required
                            value={passenger.passengerName}
                            onChange={(event) =>
                              handlePassengerChange(
                                index,
                                "passengerName",
                                event.target.value
                              )
                            }
                          />
                        </td>
                        <td>
                          <label htmlFor={`passengerAge${index}`}>Age:</label>
                          <input
                            type="number"
                            id={`passengerAge${index}`}
                            name={`passengerAge${index}`}
                            className="form-control"
                            placeholder="Age"
                            required
                            min="1"
                            value={passenger.age}
                            onChange={(event) =>
                              handlePassengerChange(
                                index,
                                "age",
                                event.target.value
                              )
                            }
                          />
                        </td>
                        <td>
                          <label htmlFor={`passengerGender${index}`}>Gender:</label>
                          <select
                            id={`passengerGender${index}`}
                            name={`passengerGender${index}`}
                            className="form-control"
                            value={passenger.gender}
                            onChange={(event) =>
                              handlePassengerChange(index, "gender", event.target.value)
                            }
                            required
                          >
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Others">Others</option>
                          </select>

                        </td>
                        <td className="text-center" style={{ paddingTop: '20px' }}>
                          <button
                            type="button"
                            className="btn btn-danger"
                            onClick={() => removePassenger(index)}
                          >
                            Remove
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
                <button
                  type="button"
                  className="btn btn-primary ml-5"
                  onClick={addPassenger}
                >
                  Add Passenger
                </button>
                <br />
                <br />
              </div>
            </div>
            {/* )} */}


            { counter == reservationInput.noOfSeats && reservationInput.noOfSeats>0 && selectedTrain && customer ? (
            <div className="text-center mb-1">
              <button type="submit" className="btn btn-success btn-lg" >
                Make Reservation
              </button>
            </div>
            ): (
              <div className="text-center mb-1">
              <button type="submit" className="btn btn-success btn-lg" disabled style={{fontWeight:'bolder', backgroundColor:'#116530 '}}>
                Make Reservation
              </button>
            </div>
            )}
          </form>
          {isModalOpen && (
            <div className="modal" >
              <div className="modal-content" style={{ backgroundColor: "beige" }}>
                <span className="close" onClick={closeModal}>
                  &times;
                </span>
                <h3>Reservation Details</h3>
                {reservationOutput && (
                  <div>
                    <table className="table table-bordered table-hover">
                      <tbody>
                        <tr class="table-warning">
                          <td>Reservation ID:</td>
                          <td>{reservationOutput.reservationId}</td>
                          <td>No. of Seats:</td>
                          <td>{reservationOutput.noOfSeats}</td>
                        </tr>
                        <tr class="table-warning">
                          <td>Travel Date:</td>
                          <td>{reservationOutput.travelDate}</td>
                          <td>Reservation Date:</td>
                          <td>{reservationOutput.reservationDate}</td>
                        </tr>
                        <tr class="table-warning">
                          <td>Source:</td>
                          <td>{reservationOutput.source}</td>
                          <td>Destination:</td>
                          <td>{reservationOutput.destination}</td>
                        </tr>
                        <tr class="table-warning">
                          <td>Seat Type:</td>
                          <td>{reservationOutput.seatType}</td>
                          <td>Booking Staus:</td>
                          <td>{reservationOutput.bookingStatus}</td>
                        </tr>
                        <tr class="table-warning">
                          <td>Total Tikcet Price:</td>
                          <td>{reservationOutput.totalTicketPrice}</td>
                          <td>Train No:</td>
                          <td>{reservationOutput.trainNo}</td>
                        </tr>
                      </tbody>
                    </table>
                    <h3>Passenger Details</h3>
                    <table className="table table-bordered table-hover">
                      <tbody>
                        {reservationOutput.passengerList.map(
                          (passenger, index) => (
                            <React.Fragment key={index}>
                              <tr class="table-warning">
                                <td>Name:</td>
                                <td>{passenger.passengerName}</td>
                                <td>Age:</td>
                                <td>{passenger.age}</td>
                                <td>Gender:</td>
                                <td>{passenger.gender}</td>
                              </tr>
                            </React.Fragment>
                          )
                        )}
                      </tbody>
                    </table>
                  </div>
                )}
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
      </div>
    </>
  );
};

export default MakeReservationComponent;
