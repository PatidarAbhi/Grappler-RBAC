import {configureStore } from "@reduxjs/toolkit";
import UserSlices from "./Slices/UserSlices";
import ProjectSlices from "./Slices/ProjectSlices";
import HierachySlice from "./Slices/HierachySlice";
import TeamSlice from "./Slices/TeamSlice";
import TeamMemberSlice from "./Slices/TeamMemberSlice";
import loginSlice from '../src/Slices/loginSlice';
import LoginUserSlice from "./Slices/LoginUserSlice";
import otpSlice from "./Slices/otpSlice";
import PasswordChangeSlice from "./Slices/PasswordChangeSlice";
import TicketSlice from "./Slices/TicketSlice";

const store = configureStore({
    reducer:{
        getLoginStatus: loginSlice,
        userList : UserSlices,
        projectList : ProjectSlices,
        viewHierarchy : HierachySlice,
        teamsList : TeamSlice,
        teaMemberList : TeamMemberSlice,
        loginUser: LoginUserSlice,
        emailStatus:otpSlice,
        resetPasswordStatus: PasswordChangeSlice,
        ticketList:TicketSlice
    },
});
export default store;