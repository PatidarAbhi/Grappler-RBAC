import { createSlice } from "@reduxjs/toolkit";
import axios from 'axios';
import { notify } from "../Components/Toastify.js"
import Swal from "sweetalert2";
const initialState = {
    projects: [],
    isLoadingProject: false,
    projectError: null,
}
export const getUserProjectsData = (userId) => {
    return async (dispatch) => {
        try {
            dispatch(fetchingDataRequest());
            const response = await axios.get(`http://localhost:8080/users/project/${userId}`);
            const data = response.data.data;
            dispatch(fetchingDataSuccess(data));
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
            dispatch(fetchingDataFailure(error));
        }
    }
}

export const getUserProjectsDataByEmail = (email) => {
    return async (dispatch) => {
        try {
            dispatch(fetchingDataRequest());
            const response = await axios.get(`http://localhost:8080/users/project/email/${email}`);
            const data = response.data.data;
            dispatch(fetchingDataSuccess(data));
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
            dispatch(fetchingDataFailure(error));
        }
    }
}

export const getProjectData = () => {
    return async (dispatch) => {
        try {
            dispatch(fetchingDataRequest());
            const response = await axios.get('http://localhost:8080/projects/');
            const data = response.data;
            dispatch(fetchingDataSuccess(data));
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
            dispatch(fetchingDataFailure(error));
        }
    }
}
export const deleteProjectData = (index) => {
    return async (dispatch) => {
        try {
            const response = await axios.delete(`http://localhost:8080/projects/${index}`);
            dispatch(deletingTheProject(index))
            Swal.fire(
                'Deleted!',
                'Your Project has been deleted.',
                'success'
            )
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
        }
    }
}
export const deleteProjectTeamData = (projectId, teamId) => {
    return async (dispatch) => {
        try {
            const response = await axios.delete(`http://localhost:8080/projects/${projectId}/teams/${teamId}`);
            const object = {
                pId: projectId,
                tId: teamId,
            }
            dispatch(deletingProjectTeam(object))
            Swal.fire(
                'Deleted!',
                'Team has been deleted from this project.',
                'success'
            )
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
        }
    }
}
export const updateProjectData = (id, name) => {
    return async (dispatch) => {
        try {
            let details = { name };
            const response = await axios.put(`http://localhost:8080/projects/${id}`, details);
            dispatch(updatingProject({ id: id, details: details }));
            notify(response.data.message);
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
        }
    }
}
export const addProjectData = (project) => {
    return async (dispatch) => {
        try {
            const response = await axios.post('http://localhost:8080/projects/', project);
            const newProject = {
                id: response.data.data,
                name: project.name,
                teams:[]
            }
            dispatch(addingProject(newProject));
            notify(response.data.message);
        } catch (error) {
            if (error.response) {
                const msg = error.response.data.message;
                notify(msg);
            }
            console.error('Error storing data:', error);
        }
    }
}

export const addProjecTeamData = (projectId, teamIds) => {
    return async (dispatch) => {
        try {
            const response = await axios.post(`http://localhost:8080/projects/${projectId}/teams`, teamIds);
            const object = {
                pId: projectId,
                team: response.data.data,
            };
            dispatch(addingProjectTeam(object));
        } catch (error) {
            if (error.response) {
                const msg = error.response.data.message;
                notify(msg);
            }
            console.error('Error storing data:', error);
        }
    };
};

const projectSlice = createSlice({
    name: 'projects',
    initialState,
    reducers: {
        fetchingDataRequest: (state) => {
            state.isLoadingProject = true;
            state.projectError = null;
        },
        fetchingDataSuccess: (state, action) => {
            state.projects = action.payload;
            state.isLoadingProject = false;
            state.projectError = null;
        },
        fetchingDataFailure: (state, action) => {
            state.projects = [];
            state.isLoadingProject = false;
            state.projectError = action.payload;
        },
        addingProject: (state, action) => {
            let projects = [...state.projects];
            projects.push(action.payload);
            state.projects = projects; 
        },
        deletingTheProject: (state, action) => {
            const updatedItems = state.projects.filter((item, index) => item.id !== action.payload);
            state.projects = updatedItems;
        },
        updatingProject: (state, action) => {
            const updatedItems = state.projects.map((item) => item.id === action.payload.id ? { ...item, ...action.payload.details } : item);
            state.projects = updatedItems;
        },
        addingProjectTeam: (state, action) => {
            const { pId, team } = action.payload;
            const updatedProjects = state.projects.map((project) => {
                if (project.id === pId) {
                    return {
                        ...project,
                        teams: [...project.teams, team],
                    };
                }
                return project;
            });

            return {
                ...state,
                projects: updatedProjects,
            };
        },
        deletingProjectTeam: (state, action) => {
            const { pId, tId } = action.payload;
            const projectToUpdate = state.projects.find((project) => project.id === pId);

            if (!projectToUpdate) {
                return state; 
            }

            const updatedTeams = projectToUpdate.teams.filter((team) => team.id !== tId);

            // Create a new project object with the updated teams array
            const updatedProject = {
                ...projectToUpdate,
                teams: updatedTeams,
            };

            // Create a new array of projects with the updated project
            const updatedProjects = state.projects.map((project) => {
                if (project.id === pId) {
                    return updatedProject;
                }
                return project;
            });

            return {
                ...state,
                projects: updatedProjects,
            };
        }

    }
})
export const {
    fetchingDataRequest, fetchingDataSuccess, fetchingDataFailure, deletingTheProject, updatingProject, addingProject, addingProjectTeam, deletingProjectTeam
} = projectSlice.actions;
export default projectSlice.reducer;

