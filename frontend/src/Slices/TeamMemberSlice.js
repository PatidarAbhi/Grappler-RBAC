import { createSlice } from "@reduxjs/toolkit";
import axios from 'axios';
import { toast } from "react-toastify";
import Swal from "sweetalert2";
// import { useSelector } from "react-redux";
const initialState = {
    teamMember: [],
    isLoadingTeamMember: false,
    teamMemberError: null,
}
export const getTeamMemberData = (teamId) =>{
    return async(dispatch) =>{
        try {
          dispatch(fetchingTeamMemberRequest());
          const response = await axios.get(`http://localhost:8080/team-members/${teamId}`);
          const data = response.data;
          dispatch(fetchingTeamMemberSuccess(data));
        } catch (error) {
          dispatch(fetchingTeamMemberFailure(error));
        }
    }
}
export const deleteTeamMemberData = (teamId, userId) =>{
    return async(dispatch) =>{
        try {
            const response = await axios.delete(`http://localhost:8080/team-members/${teamId}/delete-member/${userId}`);
            dispatch(deletingTheTeamMember(userId))

            Swal.fire(
                'Deleted!',
                'Team Member has been deleted.',
                'success'
            )
            
        } catch (error) {
           
        }
    }
}
export const updateTeamMemberData = (id, name) =>{
    return async(dispatch) =>{
        try {
            let details = { name };
            const response = await axios.put(`http://localhost:8080/projects/${id}`, details);
            dispatch(updatingTeamMember({ id: id, details: details }));
          } catch (error) {
          }
    }
}

const notify = (msg) => toast(msg);
export const addTeamMemberData = (teamId, userIds) =>{
    return async(dispatch, getState) =>{
        try {
            await axios.post(`http://localhost:8080/team-members/${teamId}/add-new-members`, userIds);
            const store = getState().userList;   
            dispatch(addingTeamMember({store, userIds}));
          } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
            console.error('Error storing data:', error);
          }
    }
}

export const getTeamMemberDataByProjectId = (projectId) =>{
    return async(dispatch) =>{
        try {
          dispatch(fetchingTeamMemberRequest());
          const response = await axios.get(`http://localhost:8080/team-members/team-member/${projectId}`);
          const data = response.data;
          dispatch(fetchingTeamMemberSuccess(data.data));
        } catch (error) {
          dispatch(fetchingTeamMemberFailure(error));
        }
    }
}

const teamSlice = createSlice({
    name : 'teamMember',
    initialState,
    reducers : {
        fetchingTeamMemberRequest : (state) =>{
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        },
        fetchingTeamMemberSuccess : (state, action) =>{
            return {
                ...state,
                teamMember : action.payload,
                isLoading: false,
                error: null,
            };
        },
        fetchingTeamMemberFailure : (state, action) =>{
            return {
                ...state,
                teamMember: [],
                isLoading: false,
                error: action.payload,
            };
        },
        addingTeamMember: (state, action) => {
            const { store, userIds } = action.payload;
            let teamMember = [...state.teamMember];
            const newMembers = store.users.filter(user => userIds.includes(user.id));
            teamMember = [...teamMember, ...newMembers];
            state.teamMember = teamMember;
        },

        deletingTheTeamMember : (state, action) =>{
            const updatedItems = state.teamMember.filter((item) => item.id !== action.payload);
            return {
                ...state,
                teamMember: updatedItems,
            }
        },
        updatingTeamMember : (state, action) =>{
            const updatedItems = state.teamMember.map((item) => item.id === action.payload.id ? { ...item, ...action.payload.details } : item);
            return {
                ...state,
                teamMember: updatedItems,
            };
        }
    }
})
export const {
    fetchingTeamMemberRequest, fetchingTeamMemberSuccess, fetchingTeamMemberFailure, deletingTheTeamMember, updatingTeamMember, addingTeamMember
} = teamSlice.actions;
export default teamSlice.reducer;
  
