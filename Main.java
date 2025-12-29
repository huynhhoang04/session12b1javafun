package ra.presentation;

import ra.business.AppointmentBusiness;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n**************** QUẢN LÝ LỊCH HẸN ****************");
            System.out.println("1. Thêm lịch hẹn");
            System.out.println("2. Hiển thị danh sách lịch hẹn");
            System.out.println("3. Tìm kiếm lịch hẹn theo tên bệnh nhân");
            System.out.println("4. Cập nhật lịch hẹn theo mã lịch hẹn");
            System.out.println("5. Xóa lịch hẹn theo mã lịch hẹn");
            System.out.println("6. Thống kê");
            System.out.println("7. Thoát");
            System.out.println("**************************************************");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            switch (choice) {
                case 1:
                    AppointmentBusiness.addAppointment(scanner);
                    break;
                case 2:
                    AppointmentBusiness.displayAppointments();
                    break;
                case 3:
                    AppointmentBusiness.searchByPatientName(scanner);
                    break;
                case 4:
                    AppointmentBusiness.updateAppointment(scanner);
                    break;
                case 5:
                    AppointmentBusiness.deleteAppointment(scanner);
                    break;
                case 6:
                    AppointmentBusiness.statistics();
                    break;
                case 7:
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                default:
                    System.err.println("Lựa chọn không hợp lệ, vui lòng chọn lại.");
            }

        } while (choice != 7);
    }
}
