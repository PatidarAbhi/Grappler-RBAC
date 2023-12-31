import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "react-bootstrap/Button";
import {Form} from "react-bootstrap";
import Container from "react-bootstrap/esm/Container";
import { toast } from "react-toastify";
import { useDispatch, useSelector } from "react-redux";
import { notify } from "./Toastify";
const AddName = ({value, method, navigator}) => {
    //const { projects} = useSelector((state) => state.projectList);
    const { projects } = useSelector((state) => ({
      projects: Array.isArray(state.projectList.projects) ? state.projectList.projects : [],
    }));
    
    const navigate = useNavigate();
  const dispatch = useDispatch(); 
  const [inputName, setInputName] = useState("");
  const notify = (msg) => toast(msg);
  // const [isInputEmpty, setIsInputEmpty] = useState(false);
  const handleSubmit = async (e) => {
    e.preventDefault();
    addData(inputName);
  };
  const addData = (name1) => {
    const project = {
      id: '',
      name: name1
    };
    // Check if the name already exists in the projects array
    const nameExists = projects.some((project) => project.name === name1);
    if (nameExists) {
      notify("Project name already exists");
      return;
    }
    dispatch(method(project));
    navigate("/admin/projects");
  };
  return (
    <div className="formParent">
      <Container
        className="formParent-container"
        style={{ display: "flex", justifyContent: "center" }}
      >
        <Form style={{ width: "70%" }} onSubmit={handleSubmit}>
          <Form.Group className="mb-3" controlId="formBasicName">
            <Form.Label className="text-center">Name</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Enter Name"
              value={inputName}
              onChange={(e) => setInputName(e.target.value)}
            />
          </Form.Group>
          <Button
            variant="danger"
            type="submit"
            style={{ margin: "20px" }}
            // onClick={() =>
            // }
          >
            {value}
          </Button>
        </Form>
      </Container>
    </div>
  )
}
export default AddName