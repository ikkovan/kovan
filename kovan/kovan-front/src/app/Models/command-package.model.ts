import { Rule } from './rule.model';
import {Statement} from './statement.model'
import { Variable } from './variable.model'
export class CommandPackage {
    command :Rule;
    variables : Variable[];
    statements : Statement[];
}
