import React, { useEffect, useState } from 'react';
import Table from 'react-bootstrap/Table';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import { getTeamMemberData, addTeamMemberData, deleteTeamMemberData } from '../Slices/TeamMemberSlice';
import { getUsersData } from '../Slices/UserSlices';
import Swal from 'sweetalert2';
import Select from 'react-select';
import { notify } from '../Components/Toastify';

const TeamMember = () => {
  const { teamMember } = useSelector((state) => state.teaMemberList);
  // const { users } = useSelector((state) => state.userList);
  const { users, isLoading, error } = useSelector((state) => ({
    users: Array.isArray(state.userList.users) ? state.userList.users : [],
    isLoading: state.userList.isLoading,
    error: state.userList.error,
  }));
  const { id } = useParams();
  const dispatch = useDispatch();
  const [selectedUsers, setSelectedUsers] = useState([]);

  // const userOptions = users.map((user) => ({
  //   value: user.id,
  //   label: user.name,
  // }));

  const userOptions = users
    .filter(user => !teamMember.some(teamUser => teamUser.id === user.id))
    .map((user) => ({
      value: user.id,
      label: user.name,
    }));

  useEffect(() => {
    dispatch(getTeamMemberData(id));
    dispatch(getUsersData(false));
  }, []);

  const [show, setShow] = useState(false);

  const handleClose = () => 
  {
    setSelectedUsers([]);
    setShow(false);
  }

  const addUserToTeam = () => {
    if (selectedUsers.length > 0) {
      dispatch(addTeamMemberData(id, selectedUsers))
        .then(() => {
          notify("Team member(s) added successfully");
          handleClose();
        })
        .catch((error) => {
          notify("Error adding team member");
        });
    } else {
      notify("Please Select Members");
    }
  };
  
  
  const deleteTeam = (teamId, userId) => {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        dispatch(deleteTeamMemberData(teamId, userId));
      }
    });
  };

  const customStyles = {
    control: (provided, state) => ({
      ...provided,
      border: "1px solid #ced4da",
      borderRadius: "4px",
      width: "100%",
    }),
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  };

  return (
    <div>
      <Table striped bordered hover variant="primary">
        <thead>
          <tr>
            <th>id</th>
            <th>Name</th>
            <th>Email</th>
            <th>Designation</th>
            <th>Delete</th>
          </tr>
          {teamMember !== undefined &&
            teamMember.map((item, index) => (
              <tr key={index}>
                <th>{item.id}</th>
                <th>{item.name}</th>
                <th>{item.email}</th>
                <th>{item.designation}</th>
                <th>
                  <button 
                    className="tableButton"
                    onClick={() => deleteTeam(id, item.id)}
                  >
                    Delete
                  </button>
                </th>
              </tr>
            ))}
        </thead>
      </Table>

      <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'center', width: '100%' }}>
        <Button variant="primary" onClick={() => setShow(true)}>
          ADD TEAM MEMBER
        </Button>
      </div>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>ADD Form</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form style={{ width: '40%' }} onSubmit={handleSubmit}>
           
            <Form.Group className="mb-3" controlId="userDropdown">
              <Form.Label>User</Form.Label>
              <Select
                styles={customStyles}
                isMulti
                value={selectedUsers.map((userId) => userOptions.find((user) => user.value === userId))}
                onChange={(selected) => setSelectedUsers(selected.map((user) => user.value))}
                options={userOptions}
              />
            </Form.Group>
            <Button
              variant="primary"
              type="submit"
              style={{ margin: '20px' }}
              onClick={() => addUserToTeam()}
            >
              Save Changes
            </Button>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default TeamMember;
