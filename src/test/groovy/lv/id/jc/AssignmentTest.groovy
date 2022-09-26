package lv.id.jc

import lv.id.jc.worker.Worker
import lv.id.jc.workscope.WorkScope
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Assignment)
@Title('Work Assignment')
class AssignmentTest extends Specification {
    def workScope = Stub WorkScope
    def worker = Stub Worker

    def "should calculate fund balance for work assignment"() {

        given: 'worker with defined salary and bonus'
        worker.salary(workScope) >> workerSalary
        worker.personalBonus(_ as BigDecimal) >> workerBonus

        and: 'work scope with defined bill'
        workScope.bill() >> zonesBill

        when: 'we create an assignment'
        def assignment = new Assignment(worker, workScope, VendorBonus.of(vendorBonus))

        then: 'we get calculated fund balance'
        assignment.fundBalance() == expectedFundBalance

        where:
        workerSalary | vendorBonus | workerBonus | zonesBill
        400          | 100         | 100         | 1000

        and: 'the balance of funds must be calculated using the formula'
        expectedFundBalance = zonesBill - (workerSalary + workerBonus)
    }
}
