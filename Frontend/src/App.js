import React, { useEffect, useState } from "react";
import "./App.css";
import Login from "./Login";
import StudentMap from "./StudentMap";

function App() {

  // ================= STATE =================
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState(null);
  const [currentPage, setCurrentPage] = useState("students");
  
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  
  const [editingId, setEditingId] = useState(null);

  // ================= LOGIN =================
  const handleLogin = (role) => {
    setIsLoggedIn(true);
    setUserRole(role);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setUserRole(null);
    setCurrentPage("students");
  };

  // ================= GET ALL STUDENTS =================
  const fetchStudents = () => {
    setLoading(true);
    setError(null);
    fetch("http://localhost:8080/api/students")
      .then(response => {
        if (!response.ok) throw new Error("Failed to fetch students");
        return response.json();
      })
      .then(data => {
        setStudents(data);
        setLoading(false);
      })
      .catch(error => {
        console.error("Error fetching students:", error);
        setError(error.message);
        setLoading(false);
      });
  };

  useEffect(() => {
    if (isLoggedIn) {
      fetchStudents();
    }
  }, [isLoggedIn]);

  // ================= ADD/UPDATE STUDENT =================
  const addStudent = (e) => {
    e.preventDefault();

    const student = { firstName, lastName, email };

    if (editingId) {
      // UPDATE
      fetch(`http://localhost:8080/api/students/${editingId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(student)
      })
        .then(res => res.json())
        .then(updatedStudent => {
          setStudents(students.map(s => s.id === editingId ? updatedStudent : s));
          resetForm();
        })
        .catch(err => console.log(err));
    } else {
      // ADD
      fetch("http://localhost:8080/api/students", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(student)
      })
        .then(res => res.json())
        .then(newStudent => {
          setStudents([...students, newStudent]);
          resetForm();
        })
        .catch(err => console.log(err));
    }
  };

  // ================= EDIT STUDENT =================
  const editStudent = (student) => {
    setFirstName(student.firstName);
    setLastName(student.lastName);
    setEmail(student.email);
    setEditingId(student.id);
  };

  // ================= RESET FORM =================
  const resetForm = () => {
    setFirstName("");
    setLastName("");
    setEmail("");
    setEditingId(null);
  };

  // ================= DELETE STUDENT =================
  const deleteStudent = (id) => {
    if (!window.confirm("Are you sure you want to delete this student?")) return;

    fetch(`http://localhost:8080/api/students/${id}`, { method: "DELETE" })
      .then(() => setStudents(students.filter(student => student.id !== id)))
      .catch(err => console.log(err));
  };

  // ================= UI =================
  if (!isLoggedIn) {
    return <Login onLogin={handleLogin} />;
  }

  return (
    <div className="app-container">

      {/* NAVIGATION BAR */}
      <nav className="navbar">
        <div className="nav-brand">
          <h1>🎓 Student Management System</h1>
        </div>
        <div className="nav-links">
          <button 
            className={currentPage === "students" ? "nav-btn active" : "nav-btn"}
            onClick={() => setCurrentPage("students")}
          >
            📋 Students
          </button>
          <button 
            className={currentPage === "map" ? "nav-btn active" : "nav-btn"}
            onClick={() => setCurrentPage("map")}
          >
            🗺️ Map
          </button>
          <div className="nav-user">
            <span>👤 {userRole.toUpperCase()}</span>
            <button onClick={handleLogout} className="logout-btn">Logout</button>
          </div>
        </div>
      </nav>

      {/* CONTENT AREA */}
      <div className="content">
        {currentPage === "students" && (
          <>
            {/* ADD STUDENT FORM - Only for Admin */}
            {userRole === "admin" && (
              <form className="student-form" onSubmit={addStudent}>
                <input
                  type="text"
                  placeholder="First Name"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  required
                />

                <input
                  type="text"
                  placeholder="Last Name"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  required
                />

                <input
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />

                <button type="submit">{editingId ? "Update Student" : "Add Student"}</button>
                {editingId && <button type="button" onClick={resetForm} className="cancel-btn">Cancel</button>}
              </form>
            )}

            {/* LOADING */}
            {loading && <div className="loading">Loading students...</div>}

            {/* ERROR */}
            {error && <div className="error">❌ {error}</div>}

            {/* TABLE */}
            {!loading && !error && (
              <div className="table-container">
                <table className="student-table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>Email</th>
                      <th>Action</th>
                    </tr>
                  </thead>

                  <tbody>
                    {students.map(student => (
                      <tr key={student.id}>
                        <td>{student.id}</td>
                        <td>{student.firstName}</td>
                        <td>{student.lastName}</td>
                        <td>{student.email}</td>
                        <td>
                          {userRole === "admin" && (
                            <>
                              <button className="edit-btn" onClick={() => editStudent(student)}>Edit</button>
                              <button className="delete-btn" onClick={() => deleteStudent(student.id)}>Delete</button>
                            </>
                          )}
                          {userRole === "user" && <span className="view-only">View Only</span>}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>

                <div className="footer">
                  Total Students: {students.length}
                </div>
              </div>
            )}
          </>
        )}

        {currentPage === "map" && <StudentMap />}
      </div>

    </div>
  );
}

export default App;
