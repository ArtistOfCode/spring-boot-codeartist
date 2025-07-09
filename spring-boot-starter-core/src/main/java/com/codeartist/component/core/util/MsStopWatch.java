package com.codeartist.component.core.util;


import org.springframework.util.StopWatch;

import java.text.NumberFormat;

/**
 * 毫秒单位的StopWatch
 *
 * @author AiJiangnan
 * @date 2025/7/9
 */
@SuppressWarnings("NullableProblems")
public class MsStopWatch extends StopWatch {

    @Override
    public String shortSummary() {
        return "StopWatch '" + getId() + "': running time = " + getTotalTimeMillis() + " ms";
    }

    @Override
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        sb.append("---------------------------------------------\n");
        sb.append("ms         %     Task name\n");
        sb.append("---------------------------------------------\n");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(6);
        nf.setGroupingUsed(false);
        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);
        for (TaskInfo task : getTaskInfo()) {
            sb.append(nf.format(task.getTimeMillis())).append("  ");
            sb.append(pf.format((double) task.getTimeNanos() / getTotalTimeNanos())).append("  ");
            sb.append(task.getTaskName()).append('\n');
        }
        return sb.toString();
    }
}
