import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import { useSelector, useDispatch } from "react-redux";
import { getTeamData, deleteTeamData, updateTeamData, addTeamData, deleteTeam } from "../Slices/TeamSlice";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import { useNavigate } from "react-router-dom";
import { getUsersData, deleteUserData } from "../Slices/UserSlices";
import { toast } from "react-toastify";
import Swal from "sweetalert2";
import Select from 'react-select';


const Teams = () => {
  const { teams, isTeamsLoading, teamsError } = useSelector((state) => state.teamsList);
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [teamId, setTeamId] = useState("");
  const [show, setShow] = useState(false);
  const [addShow, setAddShow] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState([]);
  // const { users } = useSelector((state) => state.userList);
  const { users, isLoading, error } = useSelector((state) => ({
    users: Array.isArray(state.userList.users) ? state.userList.users : [],
    isLoading: state.userList.isLoading,
    error: state.userList.error,
  }));
  const [numUserIds, setNumUserIds] = useState(1);
  const dispatch = useDispatch();

  const handleClose = () => {
    setShow(false);
    setAddShow(false);
    setSelectedUsers([]);
  };

  const updateTeam = (name) => {
    const teamNameExists = teams.some((team) => team.name === name);
    if (teamNameExists) {
      notify("Team with the same name already exists");
    } else if (name) {
      dispatch(updateTeamData(teamId, name));
      handleClose();
    }
  };

  const userOptions = users.map((user) => ({
    value: user.id,
    label: user.name,
  }));

  const notify = (msg) => {
    toast(msg);
  };

  const addTeam = (name) => {
    if (!selectedUsers || selectedUsers.length === 0) {
      notify("At least one team member should be selected");
      return;
    }

    const teamNameExists = teams.some((team) => team.name === name);
    if (teamNameExists) {
      notify("Team with the same name already exists");
      return;
    }

    if (name) {
      const teamData = {
        id: "",
        name: name,
        teamMembers: selectedUsers.map((userId) => ({ user: { id: userId } })),
      };
      dispatch(addTeamData(teamData));
      handleClose();
    }
  };

  const removeSelectedUser = (userId) => {
    const updatedSelectedUsers = selectedUsers.filter((id) => id !== userId);
    setSelectedUsers(updatedSelectedUsers);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  };

  const handleButtonClick = (item) => {
    setName(item.name);
    setTeamId(item.id);
    setShow(true);
  };

  const handleAddButtonClick = () => {
    setAddShow(true);
  };

  const teamMember = (IdTeam) => {
    navigate(`/admin/teams/${IdTeam}/teamDetails`);
  };

  const teamDelete = (teamId) => {
    if (teamId) {
      Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!",
      }).then((result) => {
        if (result.isConfirmed) {
          dispatch(deleteTeam(teamId));
        }
      });
    }
  };

  const customStyles = {
    control: (provided, state) => ({
      ...provided,
      border: "1px solid #ced4da",
      borderRadius: "4px",
      width: "100%",
    }),
  };

  useEffect(() => {
    dispatch(getTeamData());
    dispatch(getUsersData(false));
  }, [dispatch]);

  if (isTeamsLoading) {
    return <div>Loading...</div>;
  }

  if (teamsError) {
    return <div>Error: {teamsError.message}</div>;
  }

  return (
    <div>
      <Table striped bordered hover variant="success">
        <thead>
          <tr>
            <th>Team Id</th>
            <th>Name</th>
            <th>Teams Members</th>
            <th>Edit</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {teams !== undefined &&
            teams.map((item, index) => (
              <tr key={index}>
                <th>{item.id}</th>
                <th>{item.name}</th>
                <th>
                  <button className="tableButton" onClick={() => teamMember(item.id)}>
                    View Members
                  </button>
                </th>
                <th>
                  <button className="tableButton" onClick={() => handleButtonClick(item)}>
                    Edit
                  </button>
                </th>
                <th>
                  <button className="tableButton" onClick={() => teamDelete(item.id)}>
                    Delete
                  </button>
                </th>
              </tr>
            ))}
        </tbody>
      </Table>
      <Button variant="primary" type="submit" style={{ margin: "20px" }} onClick={handleAddButtonClick}>
        ADD TEAM
      </Button>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Form</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form style={{ width: "40%" }} onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="Name">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Name"
                defaultValue={name || ""}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </Form.Group>
            <Button
              variant="primary"
              type="submit"
              style={{ margin: "20px" }}
              onClick={() => updateTeam(name)}
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
      <Modal show={addShow} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add Team</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form style={{ width: "40%" }} onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="Name">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter New Team Name"
                defaultValue={""}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </Form.Group>
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
              style={{ margin: "20px" }}
              onClick={() => addTeam(name)}
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

export default Teams;
