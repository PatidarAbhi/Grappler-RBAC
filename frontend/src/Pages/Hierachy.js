

import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { getHeirarchyData, updateHierarchy } from "../Slices/HierachySlice";
import { Tree, TreeNode } from "react-organizational-chart";
import { useDrag, useDrop } from "react-dnd";
import _ from "lodash";
import clsx from "clsx";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Box from "@material-ui/core/Box";
import IconButton from "@material-ui/core/IconButton";
import BusinessIcon from "@material-ui/icons/Business";
import Avatar from "@material-ui/core/Avatar";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import Badge from "@material-ui/core/Badge";
import Tooltip from "@material-ui/core/Tooltip";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import { createMuiTheme, makeStyles, ThemeProvider } from "@material-ui/core/styles";
import { toast } from "react-toastify";

const useStyles = makeStyles((theme) => ({
  root: {
    background: "white",
    display: "inline-block",
    borderRadius: 16
  },
  expand: {
    transform: "rotate(0deg)",
    marginTop: -10,
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.short
    })
  },
  expandOpen: {
    transform: "rotate(180deg)"
  },
  avatar: {
    backgroundColor: "#ECECF4"
  }
}));

function Organization({ org, onCollapse, collapsed, onDrop }) {
  const classes = useStyles();

  // const [{ canDrop, isOver }, drop] = useDrop({
  //   accept: "ORGANIZATION",
  //   drop: () => {
  //     console.log("Dropping organization:", org);
  //     onDrop(org);
  //   },
  //   collect: (monitor) => ({
  //     isOver: monitor.isOver(),
  //     canDrop: monitor.canDrop()
  //   })
  // });

  const [{ canDrop, isOver, item }, drop] = useDrop({
    accept: "ORGANIZATION",
    drop: (droppedOrg, monitor) => {
      const draggedOrg = monitor.getItem().org;
      onDrop(droppedOrg, draggedOrg);
    },
    collect: (monitor) => ({
      isOver: monitor.isOver(),
      canDrop: monitor.canDrop(),
      item: monitor.getItem()
    })
  });
  

  const hasSubordinates = _.size(org?.subordinates) > 0;

  const [{ isDragging }, drag] = useDrag({
    type: "ORGANIZATION",
    item: { org: { ...org }, type: "ORGANIZATION" },
    canDrag: true,
    collect: (monitor) => ({
      isDragging: monitor.isDragging()
    })
  });

  const isActive = canDrop && isOver;
  let backgroundColor = "white";
  if (isActive) {
    backgroundColor = "#ddffd2";
  } else if (canDrop) {
    backgroundColor = "#ffeedc";
  }

  return (
    <Card
      variant="outlined"
      className={classes.root}
      ref={(node) => drag(drop(node, { org, onDrop }))}
      style={{
        backgroundColor,
        opacity: isDragging ? 0.5 : 1,
        cursor: isDragging || (canDrop && isOver) ? "grabbing" : "grab"
      }}
    >
      <CardHeader
        avatar={
          <Tooltip title={`${_.size(org?.subordinates)}`} arrow>
            <Badge
              style={{ cursor: "pointer" }}
              color="secondary"
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "right"
              }}
              showZero
              invisible={!collapsed}
              overlap="circle"
              badgeContent={_.size(org?.subordinates)}
              onClick={onCollapse}
            >
              <Avatar className={classes.avatar}>
                <BusinessIcon color="primary" />
              </Avatar>
            </Badge>
          </Tooltip>
        }
        title={org?.name + ' (' + org?.designation + ')'}
      />

      <IconButton
        size="small"
        onClick={onCollapse}
        className={clsx(classes.expand, {
          [classes.expandOpen]: !collapsed
        })}
      >
        <ExpandMoreIcon />
      </IconButton>
    </Card>
  );
}

function Node({ o, parent, onDrop, onCollapse }) {
  const [collapsedMap, setCollapsedMap] = useState({});

  const handleCollapse = () => {
    console.log("Before Collapse - Previous State:", collapsedMap);
    setCollapsedMap((prevMap) => {
      const updatedMap = {
        ...prevMap,
        [o.id]: !prevMap[o.id],
      };
      console.log("After Collapse - Updated State:", updatedMap);
      return updatedMap;
    });
  };

  const T = parent
    ? TreeNode
    : (props) => (
        <Tree
          {...props}
          lineWidth={"2px"}
          lineColor={"#bbc"}
          lineBorderRadius={"12px"}
        >
          {props.children}
        </Tree>
      );

  return (
    <T
      label={
        <Organization
          org={o}
          onCollapse={handleCollapse}
          collapsed={collapsedMap[o.id] || false}
          onDrop={(draggedOrg) => onDrop(o, draggedOrg)}
        />
      }
    >
      {_.map(o.subordinates, (c) => (
        <Node
          key={c.id}
          o={c}
          parent={o}
          onDrop={onDrop}
          onCollapse={onCollapse}
        />
      ))}
    </T>
  );
}

const theme = createMuiTheme({
  palette: {
    background: "#ECECF4"
  },
  fontFamily: "Roboto, sans-serif"
});

export default function Heirachy(props) {
  const { hierarchy } = useSelector((state) => state.viewHierarchy);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getHeirarchyData());
  }, [dispatch]);

  const isSubordinate = (employee, potentialSupervisor) => {
    console.log("Employee" , employee.id);
    if (employee.id === potentialSupervisor.id) {
      return true;
    } else if (employee.subordinates) {
      return employee.subordinates.some(subordinate => isSubordinate(subordinate, potentialSupervisor));
    }
    return false;
  };

  const notify = (msg) => toast(msg);

  
  const handleDrop = (droppedOrg, draggedOrg) => {
    console.log("Dropped organization:", droppedOrg.subordinates);
    console.log("Dragged organization:", draggedOrg.org.id);

    const updateRequest = {
      draggedId: draggedOrg.org.id,
      droppedId: droppedOrg.id,
    };
  
    if (updateRequest.draggedId == updateRequest.droppedId) {
      return;
    }

    if (droppedOrg.subordinates && droppedOrg.subordinates.some(subordinate => {
      return subordinate.id === draggedOrg.org.id;
    })) {
      notify("The dragged employee is already reporting to the dropped employee.");
      return;
    }
    
    if (isSubordinate(draggedOrg.org, droppedOrg)) {
      notify("Can't drop an employee onto one of their own subordinates.");
      return;
    }
  
    
   
      dispatch(updateHierarchy(updateRequest))
        .then(() => {
          dispatch(getHeirarchyData());
        })
        .catch((error) => {
          console.error("Error updating hierarchy:", error);
        });
    
  };
  

  const handleNodeCollapse = (nodeId) => {
    // Perform any actions needed when a node is collapsed or expanded
  };

  return (
    <ThemeProvider theme={theme}>
      <Box bgcolor="background" padding={4}>
        <DndProvider backend={HTML5Backend}>
          <Node o={hierarchy} onDrop={handleDrop} onCollapse={handleNodeCollapse} />
        </DndProvider>
      </Box>
    </ThemeProvider>
  );
}
