import React, { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import { useDispatch, useSelector } from 'react-redux';
import { getAllTickets, getTicketsByUserId } from '../Slices/TicketSlice';


const MyTasks = () => {
  const [selectedStatus, setSelectedStatus] = useState('TO DO');
  const [show, setShow] = useState(false);

  const { tickets } = useSelector((state) => state.ticketList);
  const dispatch = useDispatch();

  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);

  useEffect(() => {
    dispatch(getAllTickets());
  }, [dispatch]);

  const handleStatusClick = (status) => setSelectedStatus(status);
  const status = tickets.filter((ticket1) => ticket1.status == "DONE")

  return (
    <div id='myTaskComponent'>
      <div id='taskDiv'>
        <h2>My Tasks</h2>
        <div id='divButton'>
          <button onClick={() => handleStatusClick('TO DO')} className={selectedStatus === 'TO DO' ? 'active' : ''}>To Do</button>
          <button onClick={() => handleStatusClick('IN PROGRESS')} className={selectedStatus === 'IN PROGRESS' ? 'active' : ''}>In Progress</button>
          <button onClick={() => handleStatusClick('DONE')} className={selectedStatus === 'DONE' ? 'active' : ''} >Done</button>
          <button onClick={() => handleStatusClick('ON HOLD')} className={selectedStatus === 'ON HOLD' ? 'active' : ''}>On Hold</button>
        </div>
      </div>

      <div id='divTicket'>
        <table id='taskTable'>
          <thead>
            <tr>
              <th>Task Name</th>
              <th>Task ID</th>
              <th>Priority</th>
              <th>Project Name</th>
              <th>Created By</th>
              <th>Status</th>
              <th>Created At</th>
              <th>End Date</th>
            </tr>
          </thead>
          <tbody>
            {tickets
              .filter((ticket) => ticket.status == selectedStatus)
              .map((ticket) => (
                <tr key={ticket.id}>
                  <td>{ticket.name}</td>
                  <td>{ticket.id}</td>
                  <td>{ticket.priority}</td>
                  <td>{ticket.projectName}</td>
                  <td>{ticket.creatorName}</td>
                  <td>{ticket.status}</td>
                  <td>{ticket.createdDate}</td>
                  <td>{ticket.endDate}</td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>


      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add Task</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {/* Add a form to add a new task */}
        </Modal.Body>
      </Modal>
    </div>
  );
};

export default MyTasks;
