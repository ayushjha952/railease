import Home from './components/Home';
import Navbar from './components/Navbar';
import LoginPage from './components/login';
import Register from './components/Register';
import Booking from './components/Booking';
import Displaybookings from './components/Displaybookings';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import TrainBySourceAndDestination from './components/TrainBySourceAndDestination';
import TrainByTrainNo from './components/TrainByTrainNo';
import TrainByTrainName from './components/TrainByTrainName';
import Footer from './components/Footer';
function App() {
  return (
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<Register />} />
          <Route path="/booking" element={<Booking/>} />
          <Route path="/displayallbooking" element={<Displaybookings/>} />
          <Route path="/train-by-source-and-destination" element={<TrainBySourceAndDestination/>} />
          <Route path="/train-by-train-no" element={<TrainByTrainNo/>} />
          <Route path="/train-by-train-name" element={<TrainByTrainName/>} />
        </Routes>
        <Footer />
      </Router>
  );
}

export default App;
