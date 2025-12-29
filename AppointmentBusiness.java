package ra.business;

import ra.entity.Appointment;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentBusiness {
    private static List<Appointment> appointmentList = new ArrayList<>();

    public static void addAppointment(Scanner scanner) {
        System.out.println("--- THÊM LỊCH HẸN MỚI ---");
        Appointment appt = new Appointment();
        appt.inputData(scanner, appointmentList);
        appointmentList.add(appt);
        System.out.println("Thêm lịch hẹn thành công!");
    }

    public static void displayAppointments() {
        System.out.println("--- DANH SÁCH LỊCH HẸN ---");
        if (appointmentList.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }
        appointmentList.sort(Comparator.comparing(Appointment::getAppointmentDate));
        
        appointmentList.forEach(System.out::println);
    }

    public static void searchByPatientName(Scanner scanner) {
        System.out.print("Nhập tên bệnh nhân cần tìm: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        List<Appointment> results = appointmentList.stream()
                .filter(a -> a.getPatientName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("Không tìm thấy bệnh nhân nào.");
        } else {
            System.out.println("Kết quả tìm kiếm:");
            results.forEach(System.out::println);
        }
    }

    public static void updateAppointment(Scanner scanner) {
        System.out.print("Nhập mã lịch hẹn cần cập nhật: ");
        String id = scanner.nextLine().trim();

        Optional<Appointment> optAppt = appointmentList.stream()
                .filter(a -> a.getAppointmentId().equals(id))
                .findFirst();

        optAppt.ifPresentOrElse(
            appt -> {
                System.out.println("Tìm thấy lịch hẹn cho bệnh nhân: " + appt.getPatientName());
                System.out.println("Nhập thông tin mới (Để trống để giữ nguyên):");

                System.out.print("Tên bệnh nhân mới: ");
                String name = scanner.nextLine().trim();
                if (!name.isEmpty() && name.length() >= 10 && name.length() <= 50) {
                    appt.setPatientName(name);
                }
                System.out.print("Số điện thoại mới: ");
                String phone = scanner.nextLine().trim();
                if (phone.matches("^0\\d{9}$")) {
                    appt.setPhoneNumber(phone);
                }

                System.out.print("Ngày hẹn mới (dd/MM/yyyy): ");
                String dateStr = scanner.nextLine().trim();
                if (!dateStr.isEmpty()) {
                    try {
                        LocalDate newDate = LocalDate.parse(dateStr, Appointment.DATE_FORMATTER);
                        appt.setAppointmentDate(newDate);
                    } catch (DateTimeParseException e) {
                        System.err.println("Ngày sai định dạng, giữ nguyên ngày cũ.");
                    }
                }

                System.out.print("Bác sĩ mới: ");
                String doc = scanner.nextLine().trim();
                if (!doc.isEmpty() && doc.length() <= 200) {
                    appt.setDoctor(doc);
                }

                System.out.println("Cập nhật hoàn tất!");
            },
            () -> System.err.println("Không tìm thấy mã lịch hẹn: " + id)
        );
    }

    public static void deleteAppointment(Scanner scanner) {
        System.out.print("Nhập mã lịch hẹn cần xóa: ");
        String id = scanner.nextLine().trim();

        Appointment found = null;
        for (Appointment a : appointmentList) {
            if (a.getAppointmentId().equals(id)) {
                found = a;
                break;
            }
        }

        if (found != null) {
            System.out.print("Bạn có chắc chắn muốn xóa lịch hẹn này? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("Y")) {
                appointmentList.remove(found);
                System.out.println("Đã xóa thành công.");
            } else {
                System.out.println("Đã hủy thao tác xóa.");
            }
        } else {
            System.err.println("Không tìm thấy mã lịch hẹn: " + id);
        }
    }

    public static void statistics() {
        System.out.println("--- THỐNG KÊ ---");
        System.out.println("Tổng số lịch hẹn: " + appointmentList.size());
        
        System.out.println("Số lượng lịch hẹn theo bác sĩ:");
        Map<String, Long> stats = appointmentList.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctor, Collectors.counting()));
        
        stats.forEach((doc, count) -> System.out.printf(" - Bác sĩ %s: %d lịch hẹn\n", doc, count));
    }
}
